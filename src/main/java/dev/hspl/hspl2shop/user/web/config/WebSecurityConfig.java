package dev.hspl.hspl2shop.user.web.config;

import dev.hspl.hspl2shop.user.component.ApplicationUserJwtService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain defaultSFC(
            HttpSecurity http,
            AuthenticationManager authManager,
            ObjectMapper objectMapper,
            MessageSource messageSource
    ) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/**").permitAll();

                    requests.requestMatchers("/error").permitAll();

                    requests.requestMatchers("/shop/**").permitAll();

                    requests.requestMatchers("/address/province").permitAll();
                    requests.requestMatchers("/address/province/*").permitAll();
                    requests.requestMatchers("/address/city").permitAll();

                    requests.anyRequest().authenticated();
                })
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(
                            new MainAuthenticationEntryPoint(objectMapper, messageSource)
                    );
                    //handling.accessDeniedHandler()
                })
                .addFilterBefore(
                        new JwtAuthenticationFilter(authManager),
                        UsernamePasswordAuthenticationFilter.class
                ).build();
    }

    @Bean
    public AuthenticationManager authManager(ApplicationUserJwtService jwtService) {
        ProviderManager manager = new ProviderManager(
                new JwtAuthenticationProvider(jwtService)
        );

        manager.setEraseCredentialsAfterAuthentication(true);
        //manager.setAuthenticationEventPublisher();

        return manager;
    }
}
