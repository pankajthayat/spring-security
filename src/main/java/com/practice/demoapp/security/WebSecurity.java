package com.practice.demoapp.security;

import com.practice.demoapp.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
             .permitAll()
                .anyRequest()
                .authenticated()
                .and().
                addFilter(getAuthenticationFilter())// for authentication
                .addFilter(new AutherizationFilter(authenticationManager()))// this is for authreisation
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                //addFilter(new AuthenticationFilter(authenticationManager())); // when we add this default login set up

        //Note : add authenticationFilter to webSecurity...

        //antPattern : SecurityConstants.SIGN_UP_URL  => make this in constant file
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        //userdetailsService interface helps to get details from db
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
}


// /login is default login url configured in our url..we can create custom url