package com.yeshtery.pps.configuration.security;

import com.yeshtery.pps.data.entity.appuser.AppUserRole;
import com.yeshtery.pps.service.impl.AppUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/**",
            "/",
            "/index",
            "/register",
            "/api/v1/photos/upload",// 403 problem

    };
    private final AppUserServiceImpl appUserServiceImpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String LOGIN_PAGE_URL = "/sign-in";
    private static final String LOGOUT_PAGE_URL = "/login?logout=true";
    private static final String FAILURE_PAGE_URL = "/login?error=true";

    private static final String LOGIN_PROCESSING_URL = "/login";



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint())//remove default login page when accessing forbidden URL's on the browser
                .and().anonymous()
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .antMatchers("/api/v1/**").hasAnyRole(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
                .antMatchers("/admin/**").hasAnyRole(AppUserRole.ADMIN.name())

                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE_URL)
                .loginProcessingUrl(LOGIN_PROCESSING_URL)

                .successForwardUrl("/login_success_handler")
                .failureForwardUrl("/login_failure_handler")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl(LOGOUT_PAGE_URL)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .and()
                .cors()
                .disable();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserServiceImpl);
        return provider;
    }

    static class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
        public AuthenticationEntryPoint() {
            super("");
        }

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            //todo: some user-friendly page
            response.sendError(401, "Unauthorized");
        }
    }
}
