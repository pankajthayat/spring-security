package service;


import com.practice.demoapp.io.entity.AddressEntity;
import com.practice.demoapp.io.entity.UserEntity;
import com.practice.demoapp.repository.UserRepository;
import com.practice.demoapp.service.UserService;
import com.practice.demoapp.service.impl.UserServiceImpl;
import com.practice.demoapp.shared.Utils;
import com.practice.demoapp.shared.dto.AddressDto;
import com.practice.demoapp.shared.dto.UserDto;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import  static  org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserEntity userEntity;

    private String encryptedPassword = "encryptedPssword123";
    private String userId = "testUserId1234";
    private String addressId = "testAddressId1234";

    @BeforeEach
    void setUp() throws Exception{
      //  MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setLastName("test");
        userEntity.setFirstName("test");
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail("test@test.com");
        userEntity.setUserId(userId);
        userEntity.setAddresses(getAddressesEntity());
    }

    @Test
   public  final void getUserTest(){
        // initially userEntity was created here but it is being used elsewhere also so keeping it in setup or class level...for convinience
        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("test", userDto.getFirstName());

    }

    @Test
    public final void getUserTest_UserNotFoundException(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                ()->{
                    userService.getUser("test@test.com");
                }
                );
    }

    @Test
    public void createUserTest(){


        userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setLastName("test");
        userEntity.setFirstName("test");
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail("test@test.com");
        userEntity.setUserId(userId);
        userEntity.setAddresses(getAddressesEntity());

        UserDto user = new UserDto();
        List<AddressDto> addresses = new ArrayList<>();
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("bangalore");
        addressDto.setCountry("india");
        addressDto.setPostalCode("123");
        addressDto.setStreetName("123 street name");
        addressDto.setAddressId(addressId);

        AddressDto billingaAdressDto = new AddressDto();
        billingaAdressDto.setType("billing");
        billingaAdressDto.setCity("bangalore");
        billingaAdressDto.setCountry("india");
        billingaAdressDto.setPostalCode("123");
        billingaAdressDto.setStreetName("123 street name");
        billingaAdressDto.setAddressId(addressId);


        addresses.add(addressDto);
        addresses.add(billingaAdressDto);
        user.setAddresses(addresses);
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword("test123");
        user.setEmail("test@test.com");


        when(utils.generateAddressId(anyInt())).thenReturn(addressId);
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto storredValue = userService.createUser(user);
        user.setEncryptedPassword(encryptedPassword);
        assertEquals(userEntity.getFirstName(), storredValue.getFirstName());
        assertEquals(userEntity.getLastName(), storredValue.getLastName());
        assertNotNull(storredValue.getUserId());
        assertEquals(storredValue.getAddresses().size(), userEntity.getAddresses().size());
        //verify(utils, times(2)).generateAddressId(30);//1st arg is the mock obj...2nd the no of times it is called....tlast the metghod name
        //below line id improvement fo above one

        verify(utils, times(storredValue.getAddresses().size())).generateAddressId(30);//1st arg is the mock obj...2nd the no of times it is called....tlast the metghod name

        verify(bCryptPasswordEncoder, times(1)).encode("test123");
        // there are other things we can check..like genrate user id is called once
        // save must be called on userRepo

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }



    private List<AddressDto> getAddreessDto(){
        List<AddressDto> addresses = new ArrayList<>();
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("bangalore");
        addressDto.setCountry("india");
        addressDto.setPostalCode("123");
        addressDto.setStreetName("123 street name");
        addressDto.setAddressId(addressId);

        AddressDto billingaAdressDto = new AddressDto();
        billingaAdressDto.setType("billing");
        billingaAdressDto.setCity("bangalore");
        billingaAdressDto.setCountry("india");
        billingaAdressDto.setPostalCode("123");
        billingaAdressDto.setStreetName("123 street name");
        billingaAdressDto.setAddressId(addressId);


        addresses.add(addressDto);
        addresses.add(billingaAdressDto);
        return addresses;
    }
    private List<AddressEntity> getAddressesEntity(){
        List<AddressDto> addresses = getAddreessDto();

        Type listType = new TypeToken<List<AddressEntity>>(){}.getType();
        return new ModelMapper().map(addresses, listType);
    }
}
