package com.practice.demoapp.security;

import com.practice.demoapp.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public  static String getTokenSecret(){
        AppProperties appProperties =(AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }
}
//this is not a @Compoennt or bean.... we can not autowire it