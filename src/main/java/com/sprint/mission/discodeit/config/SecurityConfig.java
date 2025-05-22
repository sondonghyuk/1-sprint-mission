package com.sprint.mission.discodeit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookieName("CSRF-TOKEN"); //CSRF 토큰을 쿠키(CSRF-TOKEN)에 저장
        cookieCsrfTokenRepository.setHeaderName("X-CSRF-TOKEN"); //저장된 CSRF 토큰을 헤더(X-CSRF-TOKEN)에 포함
        http.csrf(csrf->csrf.csrfTokenRepository(cookieCsrfTokenRepository));

        // 인증/인가 설정
        http
                .authorizeHttpRequests(auth->auth
                // CSRF 토큰을 발급하는 API는 인증하지 않음
                .requestMatchers(HttpMethod.GET,"/api/auth/csrf-token").permitAll()
                // /api/** 는 인증 필요
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                )
                //LogoutFilter 제외
                .logout(logout->logout.disable())
                .httpBasic(Customizer.withDefaults()); // 기본 HTTP Basic 인증을 사용
        return http.build();
    }

}
