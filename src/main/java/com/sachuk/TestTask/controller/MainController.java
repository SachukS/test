package com.sachuk.TestTask.controller;


import com.sachuk.TestTask.dto.ProductDto;
import com.sachuk.TestTask.dto.ShopDto;
import com.sachuk.TestTask.model.Product;
import com.sachuk.TestTask.model.Shop;
import com.sachuk.TestTask.model.User;
import com.sachuk.TestTask.service.ProductService;
import com.sachuk.TestTask.service.ShopService;
import com.sachuk.TestTask.utils.MappingUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class MainController {

    private MappingUtils utils;

    private ShopService shopService;
    private final ProductService productService;

    @GetMapping("/shops")
    public List<ShopDto> getShops(){
        List<ShopDto> shops = shopService.getShops();
        return shops;
    }

    @PostMapping("/shops/add")
    public ResponseEntity<String> addShop(@RequestBody @Valid Shop shop,
                        @AuthenticationPrincipal User user) {
        if (shopService.findAll().contains(shop))
            return new ResponseEntity<>("Shop already exist", HttpStatus.BAD_REQUEST);
        shop.setCreator(user);

        if (shop.getShopsProducts()!=null) {
            for (Product p : shop.getShopsProducts()) {
                productService.save(p);
            }
        }
        shopService.save(shop);
        return new ResponseEntity<>("Shop created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/shops/{id}")
    public ShopDto getShop(@PathVariable("id") long id) {
        ShopDto shopDto = shopService.getShopById(id);
        return shopDto;
    }

    @GetMapping("/shops/{id}/products")
    public Set<ProductDto> getProductsOfShop(@PathVariable("id") long id) {
        ShopDto shopDto = shopService.getShopById(id);
        return shopDto.getProducts();
    }

    @PostMapping("/shops/{id}/products/add")
    public ResponseEntity<String> addProductToShop(@PathVariable("id") long id,
                                             @RequestBody @Valid Product product) {
        Shop shop = shopService.findShopById(id);

        if (shop.getShopsProducts().contains(product))
            return new ResponseEntity<>("Product already exist in this shop", HttpStatus.BAD_REQUEST);

        if (!productService.findAll().contains(product))
            productService.save(product);

        shop.getShopsProducts().add(product);
        shopService.update(shop);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

}
