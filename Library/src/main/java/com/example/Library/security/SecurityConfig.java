package com.example.Library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Указываем, что будем использовать BCrypt
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для упрощения
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем доступ к Swagger UI и документации
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // Доступ к странице регистрации разрешен для всех
                        .requestMatchers("/auth/register").permitAll()

                        .requestMatchers("/reader").permitAll()
                        .requestMatchers("/auth/create").permitAll()
                        // Доступ к методу 4 (список читателей с незавершенными книгами)
                        .requestMatchers("/library/readers-unreturned-books").permitAll()
                        // Разрешаем доступ к эндпоинту /authors для всех
                        .requestMatchers("/authors/save-with-book").permitAll()
                        // Методы 1, 2 и 3 доступны только для авторизованных сотрудников
                        .requestMatchers("/api/transaction", "/api/popular-author", "/api/most-reading-client").authenticated()
                        // Остальные маршруты требуют авторизации
                        .anyRequest().authenticated()
                )
                // Настройка политики сессий (только JWT-аутентификация)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Добавляем JWT-фильтр перед UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Настройка AuthenticationManager для аутентификации
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
