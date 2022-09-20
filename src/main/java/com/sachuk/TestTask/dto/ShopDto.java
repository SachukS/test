package com.sachuk.TestTask.dto;

import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
public class ShopDto {
    Long id;
    String name;
    String address;
    Set<ProductDto> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopDto shopDto = (ShopDto) o;
        return Objects.equals(name, shopDto.name) && Objects.equals(address, shopDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
