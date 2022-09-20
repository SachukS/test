package com.sachuk.TestTask.repository;

import com.sachuk.TestTask.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(long id);

    Product findProductByName(String name);

    Product save(Product product);

    @Transactional
    Integer deleteAllByName(String name);

}
