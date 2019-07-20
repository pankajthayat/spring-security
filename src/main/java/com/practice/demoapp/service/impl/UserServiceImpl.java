package com.practice.demoapp.service.impl;

import com.practice.demoapp.io.entity.UserEntity;
import com.practice.demoapp.repository.UserRepository;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.shared.Utils;
import com.practice.demoapp.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findUserByEmail(user.getEmail()) !=null)
            throw new RuntimeException("Record already exist");
        //user.setId(12321L);
        String publicUserId = utils.generateUserId(10);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(publicUserId);
       UserEntity storedUserDetails =  userRepository.save(userEntity);
       UserDto retunedValue = new UserDto();
       BeanUtils.copyProperties(storedUserDetails, retunedValue); //for object filed beanUtils is giving error...check if it works for Object or not....for Long in  dto and enity its giving error

      return retunedValue;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
