package com.ecommerce.vedha.service;
import com.ecommerce.vedha.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Integer productId);


    Page<ProductResponse> getProducts(Pageable pageable, Integer categoryId, String keyword);
}