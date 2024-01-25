package com.mohirdev.mohirdev.config;

import com.mohirdev.mohirdev.security.JWTConfigurer;
import com.mohirdev.mohirdev.security.TokenProvider;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity

public class SecurityConfiguration {
    private final TokenProvider tokenProvider;

    public SecurityConfiguration(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder  encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/authenticate").permitAll()
                .requestMatchers("/api/register").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .apply(configurer() );

        return http.build();
    }
    private JWTConfigurer configurer(){
        return new JWTConfigurer(tokenProvider);
    }

    @Configuration
    public static class WebSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private final TokenProvider tokenProvider;

        public WebSecurityConfig(TokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            JWTConfigurer jwtConfigurer = new JWTConfigurer(tokenProvider);
            http.addFilterBefore((Filter) jwtConfigurer, UsernamePasswordAuthenticationFilter.class);
        }
    }

}
