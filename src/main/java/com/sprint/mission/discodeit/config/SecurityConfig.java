package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.security.SecurityMatchers;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity //Spring Security 활성화
@Slf4j
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize->authorize
                //API가 아닌 다른 요청은 인증을 수행하지 않음
                .requestMatchers(SecurityMatchers.NON_API).permitAll()
                .anyRequest().authenticated()
            )
            //로그아웃 관련 필터를 제외
            .logout(AbstractHttpConfigurer::disable);
        return http.build();
    }

    //기본 등록되는 필터 목록을 확인하기 위해 임시 등록 Bean
    @Bean
    public String debugFilterChain(SecurityFilterChain chain) {
        log.debug("Debug Filter Chain...");
        int filterSize = chain.getFilters().size();
        IntStream.range(0, filterSize)
            .forEach(idx -> {
                log.debug("[{}/{}] {}", idx + 1, filterSize, chain.getFilters().get(idx));
            });
        return "debugFilterChain";
    }
}
