package com.mananluvtocode.SpringMVC.api.model;

import lombok.Data;
// Mapstruct will do the mapping automatically.
@Data
public class CategoryDTO {
    private Long id;
    private String name;
}
