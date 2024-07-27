package com.mananluvtocode.SpringMVC.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mananluvtocode.SpringMVC.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String s);
}
