package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CategoryDTO;
import com.mananluvtocode.SpringMVC.mapper.CategoryMapper;
import com.mananluvtocode.SpringMVC.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String categoryName) {
        return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(categoryName));
    }
}
