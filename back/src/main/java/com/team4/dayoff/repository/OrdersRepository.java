package com.team4.dayoff.repository;

import com.team4.dayoff.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrdersRepository
 */
public interface OrdersRepository extends JpaRepository<Orders,Integer>{

    
}