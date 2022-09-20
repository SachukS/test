package com.sachuk.TestTask.service;

import com.sachuk.TestTask.dto.ProductDto;
import com.sachuk.TestTask.exception.ResourceNotFoundException;
import com.sachuk.TestTask.model.Product;
import com.sachuk.TestTask.repository.ProductRepository;
import com.sachuk.TestTask.utils.MappingUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private MappingUtils utils;

    public void create(ProductDto dto) {
        productRepository.save(utils.mapToProduct(dto));
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findByName(String name) {
        return productRepository.findProductByName(name);
    }

    public ProductDto getProductById(long id){
        Product prd = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product \"" + id +"\" not found"));
        ProductDto dto = utils.mapToProductDto(prd);
        return dto;
    }
}
