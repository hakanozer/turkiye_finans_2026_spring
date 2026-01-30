package com.works.repositories;

import com.works.entities.Customer;
import com.works.entities.projections.ICustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailEqualsIgnoreCase(String email);

    @Query(value = "select CUSTOMER.ID, CUSTOMER.NAME as name, CUSTOMER.EMAIL, R.NAME as rName  from CUSTOMER\n" +
            "inner join PUBLIC.CUSTOMER_ROLES CR on CUSTOMER.ID = CR.CUSTOMER_ID\n" +
            "inner join PUBLIC.ROLE R on R.ID = CR.ROLES_ID", nativeQuery = true)
    List<ICustomerRole> customerRoles();


    @Query(value = "select CUSTOMER.ID, CUSTOMER.NAME as name, CUSTOMER.EMAIL, R.NAME as rName  from CUSTOMER\n" +
            "inner join PUBLIC.CUSTOMER_ROLES CR on CUSTOMER.ID = CR.CUSTOMER_ID\n" +
            "inner join PUBLIC.ROLE R on R.ID = CR.ROLES_ID  where CUSTOMER.EMAIL = ?1", nativeQuery = true)
    List<ICustomerRole> customerRolesEmail(String email);


}

/*
' or 1 = 1 --
select * from customer where email = '' and password = '' or 1 = 1 --'
 */