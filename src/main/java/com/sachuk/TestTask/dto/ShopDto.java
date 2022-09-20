package com.sachuk.TestTask.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Data
public class ShopDto {
    Long id;
    @Size(min=8, message = "Name is too short, minimum 8 symbols")
    String name;
    @Size(min=20, message = "Address is too short, minimum 20 symbols")
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
