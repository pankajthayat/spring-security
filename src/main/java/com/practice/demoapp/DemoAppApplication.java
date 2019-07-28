package com.practice.demoapp;

import com.practice.demoapp.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoAppApplication extends SpringBootServletInitializer {



	// this method we have to override for war file.....and this extends this class also
	//as the tomcat is embedded in the jar file.thi make our app run on that..we have to specify the dependcy of tomcat shou be provided not at compile tim but at runtime
	// change jar to war...add depency for tomcat..scope=provided
	//
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoAppApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return  new SpringApplicationContext();
	}

	@Bean(name = "appProperties") //give cutom name by which we can get the bean in other classes
	public AppProperties getAppProperties(){
		return new AppProperties();
	}
}
