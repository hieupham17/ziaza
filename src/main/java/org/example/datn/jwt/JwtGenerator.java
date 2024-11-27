package org.example.datn.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import static org.example.datn.utils.CalendarUtil.DateTimeUtils.now;
import static org.example.datn.utils.CalendarUtil.DateTimeUtils.toJavaUtilDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtGenerator {

    JwtConfig jwtConfig;

    public String gen(Long userId, String username, String fullName, List<String> authorities, String email,
                      String phone, String role, boolean rememberMe) {
        var expirationDays = jwtConfig.getNumberOfDayExpired(rememberMe);
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("fullName", fullName)
                .claim("email", email)
                .claim("phone", phone)
                .claim("role", role)
                .claim("authorities", authorities)
                .setIssuedAt(toJavaUtilDate(now()))
                .setExpiration(toJavaUtilDate(now().plusDays(expirationDays).with(LocalTime.MAX)))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

    public String gen(Long userId, String username, String fullName, List<String> authorities
            , String email, String phone, String role, LocalDateTime exp) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("fullName", fullName)
                .claim("email", email)
                .claim("phone", phone)
                .claim("role", role)
                .claim("authorities", authorities)
                .setIssuedAt(toJavaUtilDate(now()))
                .setExpiration(toJavaUtilDate(exp))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }
}
