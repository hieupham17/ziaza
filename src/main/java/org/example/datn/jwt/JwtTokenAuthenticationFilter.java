package org.example.datn.jwt;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.datn.model.LogInformation;
import org.example.datn.utils.JsonMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    JwtReader jwtReader;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.warn("-> Start JwtTokenAuthenticationFilter <-");
            jwtReader.of(httpServletRequest)
                    .ifPresent(auth -> {
                        log.info("Auth successful: {}", JsonMapper.write(auth));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        try {
                            sendLog(httpServletRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error(e.getMessage(), e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void sendLog(HttpServletRequest request) throws Exception {

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String userLocation = request.getHeader("User-Location");
        log.info("User-Location {}", userLocation);
        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");

            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
        } catch (Exception e) {
            log.error("Error get Ip public");
        }
        LogInformation logInfo = LogInformation.getInstance();
        logInfo.updateLogInformation(
                request.getMethod(),
                request.getRequestURI(),
                in.readLine(),
                userAgent.getOperatingSystem().getName(),
                userAgent.getBrowser().getName(),
                SecurityContextHolder.getContext().getAuthentication().getCredentials().toString(),
                jwtReader.activityInAPI(request),
                request.getHeader("User-Location")
        );
        jwtReader.sendMessage("log-system", logInfo);
    }
}

