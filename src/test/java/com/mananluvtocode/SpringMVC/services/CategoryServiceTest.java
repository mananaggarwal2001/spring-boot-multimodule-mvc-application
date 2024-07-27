package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CategoryDTO;
import com.mananluvtocode.SpringMVC.domain.Category;
import com.mananluvtocode.SpringMVC.mapper.CategoryMapper;
import com.mananluvtocode.SpringMVC.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    private static final long ID = 2L;
    private static final String Name = "Jimmy";
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    void getAllCategories() {
        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        assertEquals(3, categoryDTOList.size());
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setName(Name);
        category.setId(ID);
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        CategoryDTO categoryDTO = categoryService.getCategoryByName(Name);
        assertEquals(ID, categoryDTO.getId());
        assertEquals(Name, categoryDTO.getName());
    }
}