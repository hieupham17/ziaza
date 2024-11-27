package org.example.datn.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtConfig {
    @Value("${security.jwt.header:Authorization}")
    String header;

    @Value("${security.jwt.expiration.days:1}")
    Integer expirationDays;

    @Value("${security.jwt.expiration-with-remember.days:30}")
    Integer expirationDaysWithRemember;

    @Value("${security.jwt.prefix:Bearer }")
    String prefix;

    @Value("${security.jwt.secret:Qkd9hS0P8cZVMGYTdjLy}")
    String secret;

    public Integer getNumberOfDayExpired(boolean rememberMe) {
        return rememberMe ? expirationDaysWithRemember : expirationDays;
    }
}
