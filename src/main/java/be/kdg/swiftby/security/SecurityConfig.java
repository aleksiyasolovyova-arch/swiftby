package be.kdg.swiftby.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity,
                                            GlobalAuthenticationConfigurerAdapter enableGlobalAuthenticationAutowiredConfigurer) throws Exception {
        httpSecurity.addFilterBefore(new LoginPageRedirectFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/",
                                "/login",
                                "/registration",
                                "/workInProgress"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(antMatcher("/api/**")).permitAll()
                        .requestMatchers(antMatcher("/ws/**")).permitAll()
                        // anyone with a token should be able to set up their password
                        .requestMatchers(antMatcher("/set-password/**")).permitAll()
                        .requestMatchers(antMatcher("/bikes"),
                                antMatcher("/bike-details")).permitAll()
                        //role permissions
                        .requestMatchers(antMatcher("/technician/**")).hasAnyRole("TECHNICIAN", "ADMINISTRATOR", "SUPERADMIN")
                        .requestMatchers(antMatcher("/sysadmin/unapproved-employees")).hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/sysadmin/**").hasRole("SUPERADMIN")
                        .requestMatchers(antMatcher("/startTest/**"), antMatcher("/functional-check/")).hasRole("TECHNICIAN")
                        .requestMatchers(antMatcher("/admin/technicians/*"), antMatcher("/facility/*/overview")).hasAnyRole("ADMINISTRATOR", "SUPERADMIN")
                        .requestMatchers(
                                antMatcher("/js/**"),
                                antMatcher("/webjars/**"),
                                antMatcher("/css/**"),
                                antMatcher("/images/**"),
                                // REMOVE AFTER FINISHING TESTING
                                antMatcher("/test"),
                                antMatcher("/new-test"),
                                antMatcher("/report-summary/**"),
                                // MAKE REPORT SUMMARY PAGE PUBLIC
                                antMatcher("/report-summary"),
                                antMatcher("/api/report-summaries/**")
                                ).permitAll()
                                .anyRequest().authenticated())

                .exceptionHandling(
                        exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api")) {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                            } else {
                                response.sendRedirect("/login");
                            }
                        })
                                .accessDeniedPage("/forbidden")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login.loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll())
                .build();
    };


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider())
                .build();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
