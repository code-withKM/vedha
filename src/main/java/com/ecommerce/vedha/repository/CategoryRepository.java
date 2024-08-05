package com.ecommerce.vedha.repository;

import com.ecommerce.vedha.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
