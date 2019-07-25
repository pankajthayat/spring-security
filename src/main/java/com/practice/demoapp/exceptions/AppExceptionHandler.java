package com.practice.demoapp.exceptions;

import com.practice.demoapp.modal.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {UserServiceException.class}) //give the class name here...what exception it can handle...it can have more ex class
    public ResponseEntity handleUserServiceException(UserServiceException ex, WebRequest request){
        //for only single string return
        //return new ResponseEntity(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //for any exp
    @ExceptionHandler(value = {Exception.class}) //give the class name here...what exception it can handle...it can have more ex class
    public ResponseEntity handleOtherException(UserServiceException ex, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
