package com.cbl.cityrtgs.config.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFilter authenticationFilter;
    private final JWTEntryPoint jwtEntryPoint;

    private String[] WHITELIST = {
            "/",
            "/docs/**",
            "/users/password-reset-request",
            "/users/reset-password",
            "/api/role",
            "/greeting",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/configuration/security",
            "/rtgs-service/GWClientSAService/stpa.wsdl"
    };

    private String[] WHITELIST_POST = {
            "/ibrequest",
            "test/inward-process-data",
            "/authentication/login",
            "/authentication/refresh-token"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(
                "/swagger-ui/**",
                "/configuration/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/webjars/**",
                "/configuration/security");
    }
    @Override
    protected void configure(HttpSecurity http) {


        try {
            http.cors();
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers(WHITELIST).permitAll()
                    .antMatchers(HttpMethod.POST, WHITELIST_POST)
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);

            http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return super.userDetailsService();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() {

        AuthenticationManager manager = null;

        try{
            manager = super.authenticationManagerBean();
        }
        catch(Exception e){
            log.error(e.getMessage());
        }

        return manager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder){

        try{
            builder.userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
