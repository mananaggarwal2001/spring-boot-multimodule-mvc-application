package com.mananluvtocode.SpringMVC.mapper;

import com.mananluvtocode.SpringMVC.api.model.CategoryDTO;
import com.mananluvtocode.SpringMVC.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    // this will automatically generate the class in the class file and also generate the mapping code for us to map the category to the categoryDTO.
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}
