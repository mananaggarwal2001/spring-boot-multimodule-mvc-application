package com.mananluvtocode.SpringMVC.api.model;

import lombok.Data;

import java.util.List;
@Data
public class CustomerListDTO {
    private final List<CustomerDTO> customers;
}
