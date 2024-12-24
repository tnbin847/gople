package com.bins.gople.global.config.secure;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author PARK SU BIN
 * @version v.1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 기본 인증 방식 및 CSRF 비활성화 */
        http.httpBasic().disable()
            .csrf().disable();
        /* 요청 URL에 따른 접근 제한 처리 */
        http.authorizeRequests()
                .antMatchers(SecurityConstants.PUBLICY_REQUEST_URL_MATCHERS).permitAll()
                .anyRequest().authenticated()
            .and()
                // 기존 폼 로그인 비활성화
                .formLogin().disable();
        /* 세션 정책 설정 */
        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true);
    }
}