package com.sachuk.TestTask.dto;


import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
public class ProductDto {
    Long id;
    @Size(min=5, message = "Name is too short, minimum 5 symbols")
    String name;
    @Pattern(regexp = "^\\d+(.\\d{1,2})?$")
    String price;
    @Size(min=10, message = "Description is too short, minimum 10 symbols")
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
