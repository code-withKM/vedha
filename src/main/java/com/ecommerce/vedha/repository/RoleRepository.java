package com.ecommerce.vedha.repository;

import com.ecommerce.vedha.entity.Product;
import com.ecommerce.vedha.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
