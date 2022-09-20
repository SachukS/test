package com.sachuk.TestTask.service;

import com.sachuk.TestTask.dto.ShopDto;
import com.sachuk.TestTask.exception.ResourceNotFoundException;
import com.sachuk.TestTask.model.Shop;
import com.sachuk.TestTask.repository.ShopRepository;
import com.sachuk.TestTask.utils.MappingUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShopService {
    private MappingUtils utils;
    private ShopRepository shopRepository;

    public List<Shop> findAll(){
        return shopRepository.findAll();
    }

    public ShopDto getShopById(long id) {
        Shop shp = shopRepository.findShopById(id).orElseThrow(() -> new ResourceNotFoundException("Shop \"" + id +"\" not found"));
        ShopDto dto = utils.mapToShopDto(shp);
        return dto;
    }
    public Shop findShopById(long id) {
        return shopRepository.findShopById(id).orElseThrow(() -> new ResourceNotFoundException("Shop \"" + id +"\" not found"));
    }

    public void update(Shop shop) {
        shopRepository.save(shop);
    }

    public void save(Shop shop) {shopRepository.save(shop);}
    public void create(ShopDto dto) {
        shopRepository.save(utils.mapToShop(dto));
    }

    public List<ShopDto> getShops() {
        List<Shop> shops = shopRepository.findAll();
        List<ShopDto> dtos = new ArrayList<>();
        for (Shop s:shops) {
            dtos.add(utils.mapToShopDto(s));
        }
        return dtos;
    }
}
