package org.example.datn.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.datn.exception.AccessDeniedException;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.model.UserAuthentication;
import org.example.datn.processor.auth.AuthoritiesValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.kafka.core.KafkaTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtReader {
    JwtConfig jwtConfig;
    AuthoritiesValidator authoritiesProcessor;
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    KafkaTemplate<String, String> kafkaTemplate;


    public UserAuthentication getAuth(HttpServletRequest request) throws NotFoundEntityException, AccessDeniedException {
        return of(request)
                .orElseThrow(AccessDeniedException.ofSupplier("permission.denied"));
    }

    public Optional<UserAuthentication> of(HttpServletRequest request) throws NotFoundEntityException, AccessDeniedException {
        return Optional.ofNullable(read(request));
    }

    private UserAuthentication read(HttpServletRequest request) {
        var bearerToken = request.getHeader(jwtConfig.getHeader());
        HandlerExecutionChain handlerExecutionChain = null;

        log.info("bearerToken: {}", bearerToken);

        try {
            handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);
        } catch (Exception ignored) {}

        var url = request.getServletPath();

        if (Objects.isNull(bearerToken) || !bearerToken.startsWith(jwtConfig.getPrefix())) {
            if(Objects.nonNull(handlerExecutionChain)) saveActivity(null, null, handlerExecutionChain, url);
            return null;
        }

        var userAuthentication = parse(bearerToken);

        if(Objects.nonNull(handlerExecutionChain)) {
            if (Objects.isNull(userAuthentication)) saveActivity(null, null, handlerExecutionChain, url);
            else saveActivity(userAuthentication.getPrincipal(), userAuthentication.getCredentials(), handlerExecutionChain, url);
        }


        return userAuthentication;

    }

    public UserAuthentication parse(String bearerToken) {
        var token = bearerToken.replace(jwtConfig.getPrefix(), "");

        var claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        var username = claims.getSubject();
        log.info("Username: {}, Expire time: {}", username, claims.getExpiration());
        if (Objects.isNull(username)) {
            return null;
        }

        var userId = claims.get("userId", Long.class);
        var fullName = claims.get("fullName", String.class);
        var email = claims.get("email", String.class);
        var phone = claims.get("phone", String.class);
        var role = claims.get("role", String.class);
        var authorities = getAuthorities(userId, role, claims);

        return UserAuthentication.of(userId, username, fullName, email, phone, authorities, role);
    }

    private List<String> getAuthorities(Long userId, String role, Claims claims) {
        if (authoritiesProcessor.verifyAdminRoles(role)) {
            if (authoritiesProcessor.verifySession(userId)) {
                return authoritiesProcessor.getAuthorities(userId);
            }
        }
        return claims.get("authorities", List.class);
    }

    private void saveActivity(Long userId, String username, HandlerExecutionChain executionChain, String url) {
        authoritiesProcessor.saveActivity(userId, username, executionChain, url);
    }

    public void sendMessage(String topic, Object message) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        kafkaTemplate.send(topic, jsonStr);
    }

    public String activityInAPI(HttpServletRequest request){
        HandlerExecutionChain handlerExecutionChain = null;
        try {
            handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);
        } catch (Exception e) {
            log.error("get activity error{}", e.getMessage());
        }
        var execution = (HandlerMethod) handlerExecutionChain.getHandler();
        var apiAnnotation = execution.getMethod().getDeclaredAnnotation(ApiOperation.class);
        return apiAnnotation != null ? apiAnnotation.value() : "";
    }
}
