package com.sachuk.TestTask.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=5, message = "Name is too short, minimum 5 symbols")
    private String name;

    @Pattern(regexp = "^\\d+(.\\d{1,2})?$")
    private String price;

    @Size(min=10, message = "Description is too short, minimum 10 symbols")
    private String description;

    @ToString.Exclude
    @ManyToMany(mappedBy = "shopsProducts")
    List<Shop> shops;

    public Product(Long id, String name, String price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
