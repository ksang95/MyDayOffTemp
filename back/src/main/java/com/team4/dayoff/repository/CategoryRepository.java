package com.team4.dayoff.repository;

import java.util.List;

import com.team4.dayoff.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryRepository
 */
public interface CategoryRepository extends JpaRepository<Category,Integer>{

    List<Category> findByName(String categoryName);
}