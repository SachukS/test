package com.sachuk.TestTask.repository;

import com.sachuk.TestTask.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAll();

    Optional<Shop> findShopById(@Min(value = 1L, message = "Invalid product ID.") long id);

    Shop save(Shop shop);

    long deleteShopByName(String name);

    @Transactional
    Integer deleteAllByName(String name);
}
