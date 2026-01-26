package com.works.services;

import com.works.entities.Customer;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }


}
