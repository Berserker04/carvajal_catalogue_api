package com.carvajal.catalogue.config.auth;

import com.carvajal.auth.filter.JwtRequestFilter;
import com.carvajal.auth.interfaces.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    AuthenticationEntryPoint CustomAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/health/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/products/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/clients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/clients/{email}").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/api/v1/clients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/clients/{email}").hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/api/v1/accounts/{accountNumber}").authenticated()
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/accounts/{accountNumber}").hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.POST, "/api/v1/movements").authenticated()
//                        .requestMatchers(HttpMethod.GET,"/api/v1/movements/{email}").authenticated()
//                        .requestMatchers(HttpMethod.GET,"/api/v1/movements/all").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/api/v1/movements/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .cors(withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionConfig -> exceptionConfig
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(CustomAuthenticationEntryPoint)
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                        authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

