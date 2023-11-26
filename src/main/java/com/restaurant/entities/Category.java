package com.restaurant.entities;

import com.restaurant.dtos.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String description;
    private String image;

    public CategoryDto getCategoryDto() {
        CategoryDto category = new CategoryDto();
        category.setId(Id);
        category.setName(name);
        category.setImage(description);
        category.setDescription(image);
        return category;
    }
}
