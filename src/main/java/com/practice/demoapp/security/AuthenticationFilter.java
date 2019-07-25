package com.practice.demoapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demoapp.SpringApplicationContext;
import com.practice.demoapp.modal.request.UserLoginRequestModal;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public AuthenticationManager authenticationManager;


    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
         try{

             UserLoginRequestModal creds = new ObjectMapper()
                     .readValue(request.getInputStream(), UserLoginRequestModal.class);


                //authmaganger is used to authnicate
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        creds.getEmail(),
                        creds.getPassword(),
                        new ArrayList<>()
                        ));
         } catch (IOException ex){
            throw new RuntimeException(ex);
         }
    }

    //once the authenication is successful...this method is called by spring
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String userName = ((User)authResult.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact ();
//username will be read from authentication obj

        //this is how we can access any bean in the any part of the app(whether bean or not)
        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto =userService.getUser(userName);// heare username = email;

        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX + token);

        response.addHeader("UserID", userDto.getUserId());
    }
}

// for accessing the repository to get userIr we need to autowired it...but since this class in not a bean we cannot use autowired here
// we are create a new instance of this class in WebSecurity...cause it is not a Bean
// so we have to Access ApplicationContext and then instantiate that Bean(Repo) maunally
//to Acess the App context we are creating a seperate class...ie. Spring