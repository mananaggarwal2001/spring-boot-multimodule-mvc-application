package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String categoryName);


}
