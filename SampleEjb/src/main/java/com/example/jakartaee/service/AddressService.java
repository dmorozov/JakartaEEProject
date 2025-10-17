package com.example.jakartaee.service;

import com.example.jakartaee.entity.Address;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface AddressService {

    Address createAddress(Address address);

    Address updateAddress(Address address);

    void deleteAddress(Long id);

    Address findAddressById(Long id);

    List<Address> findAllAddresses();

    List<Address> findAddressesByContactId(Long contactId);

    long countAddresses();
}
