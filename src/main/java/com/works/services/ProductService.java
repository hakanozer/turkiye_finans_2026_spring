package com.works.services;

import com.works.entities.Product;
import com.works.entities.dtos.ProductDto;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapperDefault;
    private final CacheManager cacheManager;

    @CacheEvict(value = "products", allEntries=true)
    public Product save(ProductDto productDto) {
        Product product = modelMapperDefault.map(productDto, Product.class);
        return productRepository.save(product);
    }

    @Cacheable("products")
    public List<Product> list() {
        return productRepository.findAll();
    }

    public boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
            cacheManager.getCache("products").clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Cacheable("search")
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

    //@Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    @CacheEvict(value = "products", allEntries = true)
    public void cacheClear(){
        System.out.println("cache clear");
        evictOneSearch("a", 0);
    }

    @CacheEvict(
            value = "search",
            key = "{#q, #page}"
    )
    public void evictOneSearch(String q, int page) {
    }

}
