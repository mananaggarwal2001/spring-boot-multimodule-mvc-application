package com.mananluvtocode.SpringMVC.mapper;
import com.mananluvtocode.SpringMVC.api.model.CategoryDTO;
import com.mananluvtocode.SpringMVC.domain.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    CategoryMapper mapper= CategoryMapper.INSTANCE;
    @Test
    void categoryToCategoryDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Simple Category");
        CategoryDTO categoryDTO = mapper.categoryToCategoryDTO(category);
        assertEquals(Long.valueOf(1), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
        assertNotNull(categoryDTO);
    }
}