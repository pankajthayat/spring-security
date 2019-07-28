package com.practice.demoapp.service.impl;

import com.practice.demoapp.exceptions.UserServiceException;
import com.practice.demoapp.io.entity.UserEntity;
import com.practice.demoapp.modal.ErrorMessage;
import com.practice.demoapp.modal.ErrorMessages;
import com.practice.demoapp.repository.UserRepository;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.shared.Utils;
import com.practice.demoapp.shared.dto.AddressDto;
import com.practice.demoapp.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        for(int i=0;i<user.getAddresses().size();i++){
            AddressDto addressDto = user.getAddresses().get(i);
            addressDto.setUserDetails(user);
            addressDto.setAddressId(utils.generateAddressId(30));
            user.getAddresses().set(i, addressDto);
        }
         String publicUserId = utils.generateUserId(10);
        UserEntity userEntity = new UserEntity();
        //BeanUtils.copyProperties(user, userEntity);

        ModelMapper modelMapper = new ModelMapper();
        userEntity = modelMapper.map(user,UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(publicUserId);
         UserEntity storedUserDetails =  userRepository.save(userEntity);
       UserDto retunedValue = new UserDto();
       //BeanUtils.copyProperties(storedUserDetails, retunedValue); //for object filed beanUtils is giving error...check if it works for Object or not....for Long in  dto and enity its giving error
        retunedValue = modelMapper.map(storedUserDetails, UserDto.class);
         return retunedValue;
    }

    //by implementing this method we help spring framwork to load the user details from db by username ie.email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if(userEntity == null ) throw new UsernameNotFoundException(email); //this exp from spring

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());//User obj is from spring that implemnts userDeatils
        //this method help spring to get userdetails
        //3rd parameter(see the declartion) is Autherity which is collection..for now we are using empty arraylist

    }

    @Override
    public UserDto getUser(String email){

        UserEntity userEntity = userRepository.findUserByEmail(email);
        if(userEntity == null ) throw new UsernameNotFoundException(email);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String id){

        UserDto userDto =new UserDto();
        UserEntity userEntity =userRepository.findByUserId(id);
                if(userEntity == null)
                    throw new UsernameNotFoundException(id);
                BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(String id , UserDto user){
        UserDto userDto =new UserDto();
        UserEntity userEntity =userRepository.findByUserId(id);
        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

       UserEntity updatedDetails =  userRepository.save(userEntity);

       BeanUtils.copyProperties(updatedDetails, userDto);
        return userDto;

    }

    @Override
    public void deleteUser(String userId){
        UserEntity userEntity =userRepository.findByUserId(userId);
        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit){
        List<UserDto> returnValue = new ArrayList<>();

        //creating pagebale for findAll

        Pageable pageReq = PageRequest.of(page, limit);
       Page<UserEntity> usersPage =  userRepository.findAll(pageReq);
       List<UserEntity> users = usersPage.getContent();

       for(UserEntity userEntity: users){
           UserDto userDto =new UserDto();
           BeanUtils.copyProperties(userEntity,userDto);
           returnValue.add(userDto);
       }
        return returnValue;
    }
}
