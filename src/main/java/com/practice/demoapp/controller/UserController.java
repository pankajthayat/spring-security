package com.practice.demoapp.controller;

import com.practice.demoapp.exceptions.UserServiceException;
import com.practice.demoapp.modal.ErrorMessages;
import com.practice.demoapp.modal.UserDetailsRequestModal;
import com.practice.demoapp.modal.response.OperationStatusModal;
import com.practice.demoapp.modal.response.RequestOperationName;
import com.practice.demoapp.modal.response.UserRest;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable("id") String id){

       UserRest userRest = new UserRest();
       UserDto userDto = userService.getUserByUserId(id);
       BeanUtils.copyProperties(userDto, userRest);
       return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModal requestUserDetails) throws Exception{

        UserRest returnValue = new UserRest();
        if(requestUserDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRE_FILED.getErrorMessage());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(requestUserDetails, userDto);
        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModal userDetails){

        UserRest returnValue = new UserRest();
        if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRE_FILED.getErrorMessage());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updatedUser = userService.updateUser(id,userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModal deleteUser(@PathVariable String id){
        OperationStatusModal returnValue  = new OperationStatusModal();
//        returnValue.setOperationResult("DELETE"); //SET IT HARDCODED OR create a new enum

        userService.deleteUser(id);

        returnValue.setOprationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationName.SUCCESS.name());
        return returnValue;
    }

//page starts with 0
    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit){
        List<UserRest> returnValue = new ArrayList();
        if(page>0) page = page-1; // this is so that users do not have to enter page starting with 0
        List <UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto: users){
            UserRest userModal = new UserRest();
            BeanUtils.copyProperties(userDto,userModal);
            returnValue.add(userModal);
        }
        return returnValue;
    }
}
