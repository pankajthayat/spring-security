package com.practice.demoapp.service.impl;

import com.practice.demoapp.io.entity.AddressEntity;
import com.practice.demoapp.io.entity.UserEntity;
import com.practice.demoapp.repository.AddressRepository;
import com.practice.demoapp.repository.UserRepository;
import com.practice.demoapp.service.AddressService;
import com.practice.demoapp.shared.dto.AddressDto;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService
{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;
    @Override
        public List<AddressDto> getAddresses(String id){
        ModelMapper modelMapper = new ModelMapper();
            List<AddressDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(id);
        if(userEntity == null){
            return returnValue;

        }
        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity: addresses){
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }
            return returnValue;
        }

    @Override
    public AddressDto getAddress(String addressId) {

        AddressDto addressDto = new AddressDto();
       AddressEntity addressEntity =  addressRepository.findByAddressId(addressId);
//       if(addressEntity == null)
//           throw new NotFoundException("address not found");
        ModelMapper modelMapper = new ModelMapper();


        addressDto = modelMapper.map(addressEntity, AddressDto.class);
        return addressDto;
    }
}
