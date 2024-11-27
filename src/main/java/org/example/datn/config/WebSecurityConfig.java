package org.example.datn.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.datn.jwt.FilterConfigRegister;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and() // Bật CORS
//                .csrf().disable() // Tắt CSRF cho đơn giản
//                .authorizeRequests()
//                .anyRequest().permitAll() // Cho phép tất cả các request mà không cần xác thực
//                .and()
//                .formLogin().permitAll() // Cho phép đăng nhập cho tất cả
//                .and()
//                .logout().permitAll(); // Cho phép đăng xuất cho tất cả
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Cho phép tất cả các origin
        configuration.setAllowCredentials(false); // Không cho phép gửi thông tin xác thực
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cho tất cả các endpoint
        return source;
    }

    String favicon = "/favicon.ico";
    String defaultPermitAll = "/actuator/**,/v*/internal/**,/swagger-ui/**,/v*/api-docs/**,/v*/public/**,/api-docs/**,/tmgs-socket/**";
    String DELIM = ",";

    String[] URI_ALL_PERMIT;
    String[] URI_GET_PERMIT;
    String[] URI_POST_PERMIT;
    String[] URI_PUT_PERMIT;
    String[] URI_DELETE_PERMIT;

    FilterConfigRegister filterConfigRegister;

    public WebSecurityConfig(Environment env, FilterConfigRegister filterConfigRegister) {
        log.info("===> WebSecurityConfig <===");
        this.filterConfigRegister = filterConfigRegister;

        URI_ALL_PERMIT = makeArray(env, "env.permit-all.uri", defaultPermitAll);
        URI_GET_PERMIT = makeArray(env, "env.permit-get.uri", favicon);
        URI_POST_PERMIT = makeArray(env,"env.permit-post.uri", favicon);
        URI_PUT_PERMIT = makeArray(env,"env.permit-put.uri", favicon);
        URI_DELETE_PERMIT = makeArray(env,"env.permit-delete.uri", favicon);
    }
    private String[] makeArray(Environment env, String key, String defaultValue) {
        String property = env.getProperty(key);
        return (StringUtils.isEmpty(property) ? defaultValue : defaultValue + DELIM + property).split(DELIM);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and() // Bật CORS
                .csrf().disable() // Tắt CSRF cho đơn giản
                .headers().cacheControl().disable().addHeaderWriter(myHeaderWriter)
                .and()
                // Use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Handle unauthorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Add filters
        filterConfigRegister.getBeforeFilterRegistered()
                .forEach(pair -> http.addFilterBefore(pair.getLeft(), pair.getRight()));

        filterConfigRegister.getFilterRegistered()
                .forEach(pair -> http.addFilterAt(pair.getLeft(), pair.getRight()));

        filterConfigRegister.getAfterFilterRegistered()
                .forEach(pair -> http.addFilterAfter(pair.getLeft(), pair.getRight()));

        // Allow all requests without authentication
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, URI_POST_PERMIT).permitAll()
                .antMatchers(HttpMethod.GET, URI_GET_PERMIT).permitAll()
                .antMatchers(HttpMethod.PUT, URI_PUT_PERMIT).permitAll()
                .antMatchers(HttpMethod.DELETE, URI_DELETE_PERMIT).permitAll()
//                .antMatchers("/staff/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll(); // Cho phép tất cả các request mà không cần xác thực

        // Optional: Uncomment if you want to allow form login and logout
        // http
        //         .formLogin().permitAll() // Cho phép đăng nhập cho tất cả
        //         .and()
        //         .logout().permitAll(); // Cho phép đăng xuất cho tất cả
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(URI_ALL_PERMIT);
    }

    private HeaderWriter myHeaderWriter = (req, res) -> {
        var cacheControl = req.getParameter("cache-control");
        if (!StringUtils.isEmpty(cacheControl)) {
            res.setHeader("Cache-Control", cacheControl);
        }
    };

}
