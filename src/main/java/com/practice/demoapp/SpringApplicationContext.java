package com.practice.demoapp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


// its has to implemet ACA interface
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            CONTEXT = applicationContext;
    }

    //it returns the Available bean by giving it name...anywhere in our app..if we have access to this class
    public  static  Object getBean(String beanName){
        return CONTEXT.getBean(beanName);
    }
}

