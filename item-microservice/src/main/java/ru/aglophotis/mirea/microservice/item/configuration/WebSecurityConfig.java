package ru.aglophotis.mirea.microservice.item.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.aglophotis.mirea.microservice.item.filters.AuthenticationTokenFilter;
import ru.aglophotis.mirea.microservice.item.providers.CustomAuthenticationProvider;
import ru.aglophotis.mirea.microservice.item.utils.CustomAuthenticationEntryPoint;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private AuthenticationTokenFilter authTokenFilter;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList((AuthenticationProvider) new CustomAuthenticationProvider()));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/item").permitAll()
                .antMatchers(HttpMethod.GET, "/item/*").permitAll()
                .antMatchers(HttpMethod.POST, "/item/**").permitAll()
                .antMatchers(HttpMethod.GET, "/pet").permitAll()
                .antMatchers(HttpMethod.GET, "/stuff").permitAll()
                .antMatchers(HttpMethod.DELETE, "/item/*").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/item").hasAnyRole("USER")
                .anyRequest().authenticated();
        httpSecurity
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
