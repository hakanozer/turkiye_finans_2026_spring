package com.works.services;

import com.works.entities.Customer;
import com.works.entities.dtos.CustomerLoginDto;
import com.works.entities.dtos.CustomerRegisterDto;
import com.works.repositories.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final private CustomerRepository customerRepository;
    final private ModelMapper modelMapper;
    final private PasswordEncoder passwordEncoder;
    final private HttpServletRequest request;

    public Customer registerCustomer(CustomerRegisterDto customerRegisterDto) {
        Customer customer = modelMapper.map(customerRegisterDto, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer loginCustomer(CustomerLoginDto customerLoginDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(customerLoginDto.getEmail());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            boolean passwordStatus = passwordEncoder.matches(customerLoginDto.getPassword(), customer.getPassword());
            if (passwordStatus) {
                // login başarılı
                request.getSession().setAttribute("customer", customer);
                return customer;
            } else {
                return null;
            }
        }
        return null;
    }





}