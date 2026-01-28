package com.works.restcontrollers;

import com.works.entities.Product;
import com.works.entities.dtos.ProductDto;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")

public class ProductRestController {

    private final ProductService productService;

    @PostMapping("save")
    public Product save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @GetMapping("list")
    public List<Product> list() {
        return productService.list();
    }

    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    // search?q=a&catID=1
    @GetMapping("search")
    public Page<Product> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int catID,
            @RequestParam(defaultValue = "0") int page
    ){
        return productService.search(q, page);
    }



}
