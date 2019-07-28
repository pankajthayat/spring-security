package com.practice.demoapp.service;

import com.practice.demoapp.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAddresses(String id);
    AddressDto getAddress(String addressId);
}
