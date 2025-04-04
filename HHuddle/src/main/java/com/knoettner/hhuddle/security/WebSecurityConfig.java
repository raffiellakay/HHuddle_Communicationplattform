package com.knoettner.hhuddle.security;

import com.knoettner.hhuddle.security.jwt.AuthEntryPointJwt;
import com.knoettner.hhuddle.security.jwt.AuthTokenFilter;
import com.knoettner.hhuddle.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    CustomCorsConfiguration customCorsConfiguration;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //returns AuthenticationProvider, automatically used by SpringSec -checks if UserDetails == Information provided
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService((userDetailsService));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        //returns AuthenticationManager from AuthenticationProvider
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //Returns PW Encoder
        return new BCryptPasswordEncoder();
    }

   //Chain of filters that are used one after another
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//Cross-Site Request Forgery automatically with SpringSecurity - disabled! (HTTPRequests from not good websites)
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/user/getNewPw").permitAll()
                                .requestMatchers("/v3/**").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .anyRequest().authenticated()
                ).cors(c -> c.configurationSource(customCorsConfiguration));

        http.authenticationProvider(authenticationProvider());
//authenticationJwtTokenFilter is used first
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//Spring Security function that builds the chain of filters
        return http.build();
    }
}


/*
// to remove authorization while testing. can be deleted!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/**", "/api/**").permitAll() // Allow all Swagger and API paths
                        .anyRequest().permitAll() // Allow unrestricted access for testing
                )
                .cors(c -> c.configurationSource(customCorsConfiguration)); // Enable CORS configuration

        // Temporarily disable JWT token authentication for testing
        // http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
*/