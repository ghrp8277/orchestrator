package com.example.orchestrator.config;

import com.example.orchestrator.constants.SecurityPathConstants;
import com.example.orchestrator.handler.CustomLogoutHandler;
import com.example.orchestrator.handler.CustomLogoutSuccessHandler;
import com.example.orchestrator.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(SecurityPathConstants.getPublicPaths().toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.GET, SecurityPathConstants.SOCIAL_POSTS).permitAll()
                .requestMatchers(HttpMethod.GET, SecurityPathConstants.SOCIAL_DETAIL_POSTS).permitAll()
                .requestMatchers(HttpMethod.GET, SecurityPathConstants.SOCIAL_PUBLIC_DETAIL_POSTS).permitAll()
                .requestMatchers("/ws/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
//            .logout((logout) -> logout
//                .logoutUrl(SecurityPathConstants.AUTH_LOGOUT)
//                .addLogoutHandler(customLogoutHandler)
//                .logoutSuccessHandler(customLogoutSuccessHandler)
//            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
//        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin("http://192.168.0.240:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Set-Cookie");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
