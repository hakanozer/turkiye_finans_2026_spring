package com.works.services;

import com.works.configs.IAction;
import com.works.entities.Customer;
import com.works.entities.dtos.CustomerLoginDto;
import com.works.entities.dtos.CustomerRegisterDto;
import com.works.repositories.CustomerRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    final private HttpServletRequest request;
    final private HttpServletResponse response;

    final private ApplicationContext applicationContext;
    final private Map<String,IAction> actions;

    final private CustomerRepository customerRepository;
    final private ModelMapper modelMapperDefault;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtil jwtUtil;

    public Customer registerCustomer(CustomerRegisterDto customerRegisterDto) {
        Customer customer = modelMapperDefault.map(customerRegisterDto, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));
        return customerRepository.save(customer);
    }


    public Object loginCustomerJWT(CustomerLoginDto customerLoginDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(customerLoginDto.getEmail());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            boolean passwordStatus = passwordEncoder.matches(customerLoginDto.getPassword(), customer.getPassword());
            if (passwordStatus) {
                // login başarılı
                String jwt = jwtUtil.generateToken(customer);
                Map<String, String> map = new HashMap<>();
                map.put("token", jwt);

                Cookie cookie = new Cookie("access_token", jwt);
                cookie.setHttpOnly(true);
                cookie.setSecure(false); // HTTPS zorunlu
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60); // 1 saat
                cookie.setAttribute("SameSite", "Strict");

                response.addCookie(cookie);


                return map;
            } else {
                return null;
            }
        }
        return null;
    }

    public Customer loginCustomer(CustomerLoginDto customerLoginDto) {
        actions.forEach((key, value) -> {
            System.out.println("key : " + key  );
            value.print();
        });
        actions.get("customerAction").print();
        // application düzeyinde beane ulaş
        PasswordEncoder passwordEncoder1 = applicationContext.getBean(PasswordEncoder.class);
        System.out.println(passwordEncoder1.hashCode());
        System.out.println(passwordEncoder.hashCode());

        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(customerLoginDto.getEmail());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            boolean passwordStatus = passwordEncoder.matches(customerLoginDto.getPassword(), customer.getPassword());
            if (passwordStatus) {
                // login başarılı
                request.getSession().setAttribute("customerObj", customer);
                return customer;
            } else {
                return null;
            }
        }
        return null;
    }


}