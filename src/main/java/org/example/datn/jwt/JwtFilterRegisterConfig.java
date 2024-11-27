package org.example.datn.jwt;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.Pair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.List;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtFilterRegisterConfig {
    JwtReader jwtReader;

    @Bean
    @ConditionalOnMissingBean
    public FilterConfigRegister filterConfigRegister() {
        return new FilterConfigRegister() {
            @Override
            public List<Pair<GenericFilterBean, Class<? extends Filter>>> getBeforeFilterRegistered() {
                return Collections.emptyList();
            }

            @Override
            public List<Pair<GenericFilterBean, Class<? extends Filter>>> getFilterRegistered() {
                return Collections.emptyList();
            }

            @Override
            public List<Pair<GenericFilterBean, Class<? extends Filter>>> getAfterFilterRegistered() {
                return List.of(
                        new Pair<>(new JwtTokenAuthenticationFilter(jwtReader), UsernamePasswordAuthenticationFilter.class)
                );
            }
        };
    }
}
