package com.works.configs.controllers;

import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("app")
@RequiredArgsConstructor
public class IndexController {

    final private ProductService productService;
    final private ProductRepository productRepository;

    @GetMapping("")
    public String index(Model model){
        List<Product> products = productService.list();
        products.sort(
                (a, b) -> b.getId().compareTo(a.getId())
        );
        model.addAttribute("products", products);
        return "index";
    }

    @CacheEvict(value = "products", allEntries = true)
    @PostMapping("productSave")
    public String productSave(Product product) {
        productRepository.save(product);
        return "redirect:/app";
    }

}
