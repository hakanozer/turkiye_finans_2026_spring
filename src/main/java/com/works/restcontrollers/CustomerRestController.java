package com.works.restcontrollers;

import com.works.entities.Customer;
import com.works.entities.dtos.CustomerRegisterDto;
import com.works.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerRestController {

    final private CustomerService customerService;

    @PostMapping("register")
    public Customer register(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
        return customerService.registerCustomer(customerRegisterDto);
    }

}
