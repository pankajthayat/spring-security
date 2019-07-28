package service;


import com.practice.demoapp.io.entity.UserEntity;
import com.practice.demoapp.repository.UserRepository;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.service.impl.UserServiceImpl;
import com.practice.demoapp.shared.dto.UserDto;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import  static  org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;


    @Test
   public  void getUserTest(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setLastName("test");
        userEntity.setFirstName("test");
        userEntity.setEncryptedPassword("abcd4545dfc");
        userEntity.setEmail("test@test.com");
        userEntity.setUserId("test123");
        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("test", userDto.getFirstName());

    }

    @Test
    public void getUser_Exception_Test(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                ()->{
                    userService.getUser("test@test.com");
                }
                );
    }
}
