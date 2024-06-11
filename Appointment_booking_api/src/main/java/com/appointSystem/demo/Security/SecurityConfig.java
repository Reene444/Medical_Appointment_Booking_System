package com.appointSystem.demo.Security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private RSAKey rsaKey;


    @Bean
    public JWKSource<SecurityContext>jwkSource(){
        rsaKey=Jwks.generateRsa();
        JWKSet jwkset=new JWKSet(rsaKey);
        return((jwkSelector, securityContext) -> jwkSelector.select(jwkset));
    }

    @Bean
    @Primary
    public  PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}


    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);

        return new ProviderManager(authProvider);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext>jwks){
     return new NimbusJwtEncoder(jwks);
    }


    @Bean
    JwtDecoder jwtDecoder()throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }



    @Bean
    public SecurityFilterChain sercurityFilterChain(HttpSecurity http)throws Exception{
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/db-console/**"))
                .headers((headers)->headers.frameOptions((frameOptions)->frameOptions.disable()))
                .authorizeHttpRequests(authorize ->authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers("/api/v1/appointments","/api/v1/appointments/{appointment_id}").permitAll()
                        .requestMatchers("/api/v1/auth/token","/auth/users/add", "/swagger-ui/**", "/v3/api-docs/**","/db-console/**").permitAll()
                        .requestMatchers("/api/v1/auth/users").hasAnyAuthority("SCOPE_ADMIN")
                        .requestMatchers("/test/**").authenticated()
                        .requestMatchers("/api/v1/auth/profile").authenticated()
                        .requestMatchers("/api/v1/auth/profile/update-password").authenticated()
                        .requestMatchers("/api/v1/auth/profile/delete").authenticated()
                        .requestMatchers("/api/v1/auth/users/{user_id}/update-authorities/**").hasAuthority("SCOPE_ADMIN")
                         )
                  //this means the request need to be authenticated

//                .httpBasic(withDefaults())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // to do: remove these after upgrading the db from h2 infile db
                http.csrf((csrf) -> csrf.disable());
                http.headers((headers)->headers.frameOptions((frameOptions)->frameOptions.disable()));

        return http.build();



    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Specify allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
