package com.megamainmeeting.config;

import com.megamainmeeting.auth.TokenAuthManager;
import com.megamainmeeting.auth.TokenAuthenticationExceptionHandler;
import com.megamainmeeting.auth.TokenFilter;
import com.megamainmeeting.entity.UserRole;
import com.megamainmeeting.spring.controller.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    public DataSource dataSource;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/" + Endpoints.ADMIN).hasAuthority(UserRole.Admin.getAuthority())
                .and()
                .httpBasic()
                .and()
                .formLogin();
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainRest(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/" + Endpoints.BASE_API + Endpoints.REGISTER_ANONYMOUS_PATH).permitAll()
                .antMatchers("/" + Endpoints.BASE_API + "/**").hasAuthority(UserRole.User.getAuthority())
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(provideTokenProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password , enabled from admin_users where username = ? ");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from admin_users where username = ? ");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    TokenFilter provideTokenProcessingFilter() {
        AntPathRequestMatcher restRequestMatcher = new AntPathRequestMatcher("/" + Endpoints.BASE_API + "/**");
        List<RequestMatcher> requestMatcherList = new ArrayList<>();
        requestMatcherList.add(restRequestMatcher);
        TokenFilter tokenFilter = new TokenFilter();
        tokenFilter.setAuthenticationFailureHandler(new TokenAuthenticationExceptionHandler());
        tokenFilter.setAuthenticationManager(provideTokenAuthManager());
        tokenFilter.setRequiresAuthenticationRequestMatcher(new AndRequestMatcher(requestMatcherList));
        return tokenFilter;
    }

    @Bean
    TokenAuthManager provideTokenAuthManager() {
        return new TokenAuthManager();
    }
}

