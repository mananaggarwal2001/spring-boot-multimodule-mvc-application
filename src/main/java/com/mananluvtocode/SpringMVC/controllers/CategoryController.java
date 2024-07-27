package com.mananluvtocode.SpringMVC.controllers;

import com.mananluvtocode.SpringMVC.api.model.CategoryDTO;
import com.mananluvtocode.SpringMVC.api.model.CategoryListDTO;
import com.mananluvtocode.SpringMVC.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories/")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // response entity is used for getting the response from the server and then send to the client side.

    @GetMapping
    public ResponseEntity<CategoryListDTO> getAllCategories() {
        return new ResponseEntity<CategoryListDTO>(new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        System.out.println(name);
        System.out.println(categoryService.getCategoryByName(name));
        return new ResponseEntity<CategoryDTO>(categoryService
                .getCategoryByName(name), HttpStatus.OK);
    }

}
