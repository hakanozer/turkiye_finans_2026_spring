package com.works.services;

import com.works.entities.Customer;
import com.works.entities.dtos.CustomerRegisterDto;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final private CustomerRepository customerRepository;
    final private ModelMapper modelMapper;

    public Customer registerCustomer(CustomerRegisterDto customerRegisterDto) {
        Customer customer = modelMapper.map(customerRegisterDto, Customer.class);
        return customerRepository.save(customer);
    }


}
