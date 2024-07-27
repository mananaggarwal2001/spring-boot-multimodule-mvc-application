package com.mananluvtocode.SpringMVC.bootstrap;

import com.mananluvtocode.SpringMVC.domain.Category;
import com.mananluvtocode.SpringMVC.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// this commandline runner will run on the spring boot specific application not outside the spring boot application.
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final List<Category> categoryList = new ArrayList<>();

    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category dried = new Category();
        dried.setName("Dried");
        Category wet = new Category();
        wet.setName("Wet");
        Category fruits = new Category();
        fruits.setName("Fruits");
        Category exotic = new Category();
        exotic.setName("Exotic");
        Category nuts = new Category();
        nuts.setName("Nuts");
        categoryList.add(dried);
        categoryList.add(wet);
        categoryList.add(fruits);
        categoryList.add(exotic);
        categoryList.add(nuts);

        categoryRepository.saveAll(categoryList);
        System.out.println("Data loaded successfully and count is :- " + categoryRepository.count());
    }
}
