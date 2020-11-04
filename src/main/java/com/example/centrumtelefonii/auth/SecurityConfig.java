package com.example.centrumtelefonii.auth;


import com.example.centrumtelefonii.dao.JwtBlackListRepo;
import com.example.centrumtelefonii.security.CustomUserDetailsService;
import com.example.centrumtelefonii.security.JwtAuthenticationEntryPoint;
import com.example.centrumtelefonii.security.JwtAuthenticationFilter;
import com.example.centrumtelefonii.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    CustomUserDetailsService customUserDetailsService;

    JwtAuthenticationEntryPoint unauthorizedHandler;

    JwtTokenProvider jwtTokenProvider;

    JwtBlackListRepo jwtBlackListRepo;

    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, CustomUserDetailsService customUserDetailsService,
                          JwtTokenProvider jwtTokenProvider, JwtBlackListRepo jwtBlackListRepo
    ) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtBlackListRepo = jwtBlackListRepo;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService, jwtBlackListRepo);
    }


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Value("${app.requestHeader}")
    private String principalRequestHeader;

    @Value("${app.requestHeaderKey}")
    private String principalRequestValue;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(principalRequestHeader);
//        filter.setAuthenticationManager(new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                String principal = (String) authentication.getPrincipal();
//                System.out.println(principal);
//                if (!principalRequestValue.equals(principal)) {
//                    throw new BadCredentialsException("The API key was not found or not the expected value.");
//                }
//                authentication.setAuthenticated(true);
//                return authentication;
//            }
//        });

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/produkty", "/promocje", "/promocja/dzisiaj")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/klikniecie")
                .permitAll()
                .antMatchers("/static/**")
                .permitAll()
                .antMatchers("/captchavalidation")
                .permitAll()
                .antMatchers("/sendmail")
                .permitAll()
                .antMatchers("/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://centrum-telefonii.pl")
                        .allowedMethods("PUT", "DELETE", "POST", "GET")
//                        .allowedHeaders("header1", "header2", "header3")
//                        .exposedHeaders("header1", "header2")
                ;
            }
        };
    }
}
