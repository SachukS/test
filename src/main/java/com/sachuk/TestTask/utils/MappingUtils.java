package com.sachuk.TestTask.utils;

//import com.sachuk.TestTaskBlog.model.Article;

import com.sachuk.TestTask.dto.ProductDto;
import com.sachuk.TestTask.dto.ShopDto;
import com.sachuk.TestTask.model.Product;
import com.sachuk.TestTask.model.Shop;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class MappingUtils {
    public Set<ProductDto> mapToDtoSet(Set<Product> products) {
        Set<ProductDto> dtos = new HashSet<>();
        for (Product p : products) {
            dtos.add(mapToProductDto(p));
        }
        return dtos;
    }

    public Set<Product> mapToSet(Set<ProductDto> dtos) {
        Set<Product> products = new HashSet<>();
        for (ProductDto dto : dtos) {
            products.add(mapToProduct(dto));
        }
        return products;
    }

    public ProductDto mapToProductDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public Product mapToProduct(ProductDto dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setId(dto.getId());
        product.setPrice(dto.getPrice());
        product.setShops(new ArrayList<>());
        return product;
    }

    public ShopDto mapToShopDto(Shop shop){
        ShopDto dto = new ShopDto();
        dto.setId(shop.getId());
        dto.setName(shop.getName());
        dto.setAddress(shop.getAddress());
        dto.setProducts(mapToDtoSet(shop.getShopsProducts()));
        return dto;
    }

    public Shop mapToShop(ShopDto dto){
        Shop shop = new Shop();
        shop.setName(dto.getName());
        shop.setAddress(dto.getAddress());
        shop.setId(dto.getId());
        shop.setShopsProducts(mapToSet(dto.getProducts()));
        shop.setCreateDate(LocalDate.now());
        return shop;
    }
}
