package com.sprint.mission.discodeit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth->auth
                .requestMatchers("/api/**").authenticated() // /api/를 포함하면 인증 수행
                .anyRequest().permitAll()
                )
                //LogoutFilter 제외
                .logout(logout->logout.disable())
                .httpBasic(Customizer.withDefaults()); // 기본 HTTP Basic 인증을 사용
        return http.build();
    }

}
