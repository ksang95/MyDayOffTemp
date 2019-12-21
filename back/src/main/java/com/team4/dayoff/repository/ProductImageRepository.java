package com.team4.dayoff.repository;

import com.team4.dayoff.entity.ProductImage;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductImageRepository
 */
public interface ProductImageRepository extends JpaRepository<ProductImage,Integer>{
    ProductImage findTop1ByProduct_IdOrderById(int productId);
}