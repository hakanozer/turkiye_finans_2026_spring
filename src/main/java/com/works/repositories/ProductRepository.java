package com.works.repositories;

import com.works.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // select * from product where title like '%title%' or detail like '%detail%' or price = price
    Page<Product> findByTitleContainsOrDetailContainsOrPriceEqualsAllIgnoreCase(String title, String detail, Integer price, Pageable pageable);
}
