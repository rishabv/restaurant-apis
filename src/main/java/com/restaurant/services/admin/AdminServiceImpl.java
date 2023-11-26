package com.restaurant.services.admin;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.entities.Category;
import com.restaurant.entities.Product;
import com.restaurant.repositories.CategoryRespository;
import com.restaurant.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CategoryRespository categoryRespository;

    private final ProductRepository productRepository;

    @Override
    public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
        log.info(categoryDto.getName() + "," + categoryDto.getDescription() + "," + categoryDto.getImage());
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImage(categoryDto.getImage());
        Category createdCategory = categoryRespository.save(category);
        CategoryDto categoryRes = new CategoryDto();
        BeanUtils.copyProperties(createdCategory, categoryRes);
        return categoryRes;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRespository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto addProduct(Long categoryId, ProductDto request) {
        Optional<Category> categoryDetails = categoryRespository.findById(categoryId);
        if(categoryDetails.isPresent()){
            Product product = new Product();
            BeanUtils.copyProperties(request, product);
            product.setCategory(categoryDetails.get());
            Product createdProduct  =  productRepository.save(product);
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(createdProduct, productDto);
            return productDto;
        }
        return null;
    }
}
