package com.restaurant.services.admin;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    CategoryDto postCategory(CategoryDto data) throws IOException;

    List<CategoryDto> getAllCategories();

    ProductDto addProduct(Long categoryId, ProductDto request);
}
