package com.team4.dayoff.repository;


import com.team4.dayoff.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product,Integer>{

    @Query(value="SELECT COUNT(*) FROM product WHERE registerDate > DATE_SUB(NOW(), INTERVAL 24 HOUR)",nativeQuery = true)
    int countByRegisterDatein24Hours();

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.isAvailable=0 WHERE p.id= :productId")
    void disableProduct(int productId);
    
}