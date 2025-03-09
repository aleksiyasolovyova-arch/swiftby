package be.kdg.swiftby.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity, GlobalAuthenticationConfigurerAdapter enableGlobalAuthenticationAutowiredConfigurer) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/", "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(
                                antMatcher("/js/**"),
                                antMatcher("/webjars/**"),
                                antMatcher("/css/**"),
                                antMatcher("/images/**")).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api")) {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                            } else {
                                response.sendRedirect("/login");
                            }
                        }))
//                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login.loginPage("/login").permitAll())
                .build();
    };
}
