package com.restaurant.controllers;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.dtos.ProductDto;
import com.restaurant.entities.User;
import com.restaurant.services.admin.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto data) throws IOException {
        log.info("Here is the Name " + data.getName() + "");
        log.info(data.getName() + "," +data.getDescription() + "," + data.getImage());
        CategoryDto category = adminService.postCategory(data);
        if(category==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(category);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categoryDtoList = adminService.getAllCategories();
        if(categoryDtoList==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryDtoList);
    }

    @PostMapping("/{categoryId}/product")
    public ResponseEntity<?> addProduct(@PathVariable Long categoryId, @RequestBody @Valid ProductDto request, @AuthenticationPrincipal User user){
        log.info(String.valueOf(user.getId()));
        ProductDto productDto = adminService.addProduct(categoryId, request);
        if(productDto==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

//    @GetMapping("/{categoryId}/products")
//    public ResponseEntity<List<CategoryDto>> getProductsByCategory(){
//        List<CategoryDto> categoryDtoList = adminService.getAllCategories();
//        return ResponseEntity.ok(categoryDtoList);
//    }

}
