package com.works.entities.dtos;

import com.works.entities.Customer;
import com.works.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Customer}
 */
@Value
public class CustomerLoginDto implements Serializable {
    @NotNull
    @Email
    @NotEmpty
    String email;
    @NotNull
    @Size(min = 5, max = 100)
    @NotEmpty
    String password;
}