package com.sachuk.TestTask.dto;


import lombok.Data;

import java.util.Objects;

@Data
public class ProductDto {
    Long id;
    String name;
    String price;
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto dto = (ProductDto) o;
        return Objects.equals(name, dto.name) && Objects.equals(description, dto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
