package com.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

        CorsConfiguration cors_config = new CorsConfiguration();
        cors_config.setAllowCredentials(true);
        cors_config.applyPermitDefaultValues();
        cors_config.setAllowedOrigins(Arrays.asList("http://localhost:4000", "http://localhost:4200"));
        cors_config.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        cors_config.setAllowedHeaders(List.of("*"));


        http.headers(header -> header.frameOptions(fo -> fo.disable()));
        http
                .cors(cors -> cors.configurationSource(source -> cors_config))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authz -> authz
                        .pathMatchers(HttpMethod.GET, "/Public**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/public/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api-docs**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/swagger**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/auth/test").permitAll()
                        .pathMatchers(HttpMethod.POST,"/api/users").permitAll()
                        .pathMatchers(HttpMethod.GET,"/api/bookingservice/download-ticket*").permitAll()
                        .pathMatchers(HttpMethod.GET,"/api/users/findall").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.GET,"/api/users/findById*").hasAnyAuthority("ADMIN","USER")
                        .pathMatchers(HttpMethod.POST,"/api/busservice/bus/save").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.GET,"/api/busservice/bus/findall").hasAnyAuthority("ADMIN","USER")
                        .pathMatchers(HttpMethod.GET,"/api/busservice/bus/findbyid*").hasAnyAuthority("ADMIN","USER")
                        .pathMatchers(HttpMethod.GET,"/api/busservice/bus/delete*").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.POST,"/api/bookingservice/book").hasAuthority("USER")
                        .pathMatchers(HttpMethod.GET,"/api/bookingservice/book/findall").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.GET,"/api/bookingservice/book/findAllByUserId*").hasAuthority("USER")
                        .pathMatchers(HttpMethod.DELETE,"/api/bookingservice/book/cancel*").hasAnyAuthority("USER","ADMIN")
                        .pathMatchers(HttpMethod.POST,"/api/paymentservice/payment/save").hasAuthority("USER")
                        .pathMatchers(HttpMethod.GET,"/api/paymentservice/payment/findbybookingdetailsid*").hasAnyAuthority("USER","ADMIN")
                        .pathMatchers(HttpMethod.POST,"/api/feedbackservice/feedback/save").hasAuthority("USER")
                        .pathMatchers(HttpMethod.GET,"/api/feedbackservice/feedback/findAll").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.GET,"/api/searchservice/booking/findAll*").hasAnyAuthority("USER","ADMIN")
                        .pathMatchers(HttpMethod.GET,"/api/searchservice/bus/findAll*").hasAnyAuthority("USER","ADMIN")
                        .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .httpBasic(httpBasicSpec -> httpBasicSpec.disable())
                .formLogin(formLoginSpec -> formLoginSpec.disable());

        return http.build();
    }


}