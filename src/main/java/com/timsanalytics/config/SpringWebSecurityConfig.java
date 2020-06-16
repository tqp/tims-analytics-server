package com.timsanalytics.config;

import com.timsanalytics.auth.authCommon.services.AuthService;
import com.timsanalytics.auth.authCommon.services.TokenService;
import com.timsanalytics.utils.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final Environment environment;
    private final TokenService tokenService;

    @Autowired
    public SpringWebSecurityConfig(Environment environment,
                                   AuthService authService,
                                   TokenService tokenService) {
        this.environment = environment;
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(environment, authService, tokenService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class)
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                .exceptionHandling().accessDeniedPage("/login-page")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .cors()
                .configurationSource(corsConfigurationSource())

                .and()
                .logout().logoutSuccessUrl("/login-page")

                // LOCK THE ENDPOINTS DOWN HERE
                .and()
                .authorizeRequests()
                //.antMatchers("**").permitAll() // WIDE OPEN!!!

                // Front-End Resources
                .antMatchers("/*", "/*/*").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()

                // API Endpoints OPEN
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/token-exchange/**").permitAll()
                .antMatchers("/api/v1/intuit-token/**").permitAll()
                .antMatchers("/api/v1/google-auth/**").permitAll()
                .antMatchers("/api/v1/test/**").permitAll()
                .antMatchers("/api/v1/s3/**").permitAll()
                .antMatchers("/api/v1/sample-data/**").permitAll()

                .antMatchers("/api/v1/basic-database-connection/**").permitAll()

                // API Endpoints USER
                .antMatchers("/api/v1/my-profile/**").hasAnyRole("USER")
                .antMatchers("/api/v1/settings/**").hasAnyRole("USER")
                .antMatchers("/api/v1/holiday/**").hasAnyRole("USER")

                // API Endpoints MANAGER
                .antMatchers("/api/v1/app-user/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/app-role/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/app-user-role/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/infra/v1/audit/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/employee/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/time-activity/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/intuit-sync/**").hasAnyRole("MANAGER")

                // API Endpoints ADMIN

                // API Endpoints DEVELOPER
                .antMatchers("/api/v1/auto-tracker/**").hasAnyRole("DEVELOPER")
                .antMatchers("/api/v1/charter-sauce/**").hasAnyRole("DEVELOPER")
                .antMatchers("/api/v1/reality-tracker/**").hasAnyRole("DEVELOPER")
                .antMatchers("/api/v1/sample-app/**").hasAnyRole("DEVELOPER")

                // Testing Endpoints
                .antMatchers("/api/v1/endpoint-test/open/**").permitAll()
                .antMatchers("/api/v1/endpoint-test/user/**").hasAnyRole("USER")
                .antMatchers("/api/v1/endpoint-test/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/api/v1/endpoint-test/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/endpoint-test/developer/**").hasAnyRole("DEVELOPER")

                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/csrf",
                "/v2/api-docs",
                "/api-docs/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4201", "https://timsanalytics.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(
                Arrays.asList(
                        "accept",
                        "authorization",
                        "access-control-allow-headers",
                        "access-control-allow-method",
                        "content-type",
                        "origin",
                        "responsetype",
                        "x-auth-token"
                )
        );
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    @Bean
    // This bean is needed to Autowire AuthenticationManager in InternalAuthController.java
    // "Override method authenticationManagerBean in WebSecurityConfigurerAdapter to expose the
    // AuthenticationManager built using configure(AuthenticationManagerBuilder) as a Spring bean"
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
