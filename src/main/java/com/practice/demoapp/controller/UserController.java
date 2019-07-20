package com.practice.demoapp.controller;

import com.practice.demoapp.modal.UserDetailsRequestModal;
import com.practice.demoapp.modal.response.UserRest;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public String getUser(){
        return "get user called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModal requestUserDetails){

        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(requestUserDetails, userDto);
        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){

        return "put";
    }

    @DeleteMapping
    public String deleteUser(){
        return "del";
    }
}
