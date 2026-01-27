package com.works.services;

import com.works.entities.Product;
import com.works.entities.dtos.ProductDto;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Product save(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }

    public boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // search products
    public Page<Product> search(String q, int page) {
        int price = 0;
        try {
            price = Integer.valueOf(q);
        }catch (Exception e){}
        // add sort
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Product> products = productRepository.findByTitleContainsOrDetailContainsOrPriceEqualsAllIgnoreCase(q, q, price, pageable);
        return products;
    }
}
