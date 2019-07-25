package com.practice.demoapp.service;

import com.practice.demoapp.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

    public UserDto getUser(String email);
    public UserDto getUserByUserId(String email);
    public UserDto updateUser(String id, UserDto userDto);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page, int limit);

}
