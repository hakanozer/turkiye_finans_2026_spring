package com.works.restcontrollers;

import com.works.entities.Customer;
import com.works.entities.dtos.CustomerLoginDto;
import com.works.entities.dtos.CustomerRegisterDto;
import com.works.entities.projections.ICustomerRole;
import com.works.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerRestController {

    final private CustomerService customerService;

    @PostMapping("register")
    public Customer register(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
        return customerService.registerCustomer(customerRegisterDto);
    }

    @PostMapping("login")
    public Object login(@RequestBody CustomerLoginDto customerLoginDto) {
        Object obj = customerService.loginCustomerJWT(customerLoginDto);
        if (obj != null) {
            return obj;
        }
        Map<String, Object> hm = new HashMap<>();
        hm.put("status", false);
        hm.put("message", "Email or Password incorrect");
        return hm;
    }

    @GetMapping("customerRole")
    public List<ICustomerRole> customerRoles(){
        return customerService.customerRoles();
    }

    @GetMapping("customerRoleEmail")
    public List<ICustomerRole> customerRoles(@RequestParam String email){
        return customerService.customerRolesemail(email);
    }

}
