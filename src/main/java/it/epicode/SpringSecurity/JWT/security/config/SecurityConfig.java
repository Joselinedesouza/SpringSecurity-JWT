package it.epicode.SpringSecurity.JWT.security.config;

import it.epicode.SpringSecurity.JWT.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Registrazione/Login
                        .requestMatchers(HttpMethod.GET, "/eventi/**").permitAll() // Tutti possono vedere eventi

                        // Solo organizzatori
                        .requestMatchers(HttpMethod.POST, "/eventi/**").hasRole("ORGANIZZATORE")
                        .requestMatchers(HttpMethod.PUT, "/eventi/**").hasRole("ORGANIZZATORE")
                        .requestMatchers(HttpMethod.DELETE, "/eventi/**").hasRole("ORGANIZZATORE")

                        // Prenotazioni - Utenti e Organizzatori possono prenotare
                        .requestMatchers(HttpMethod.POST, "/prenotazioni/**").hasAnyRole("UTENTE", "ORGANIZZATORE")
                        .requestMatchers(HttpMethod.GET, "/prenotazioni/**").hasAnyRole("UTENTE", "ORGANIZZATORE")
                        .requestMatchers(HttpMethod.DELETE, "/prenotazioni/**").hasAnyRole("UTENTE", "ORGANIZZATORE")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
