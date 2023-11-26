package com.restaurant.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDto {
        private Long Id;
        @NotNull(message = "Category name is required")
        @NotEmpty(message = "Category name is required")
        private String name;
        private String description;
        private String image;
}
