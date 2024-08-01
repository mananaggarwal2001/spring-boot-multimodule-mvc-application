package com.mananluvtocode.SpringMVC.mapper;

import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomer_url("https://www.google.com");
        customer.setFirstName("John");
        customer.setLastName("Smith");
        CustomerDTO result = mapper.customerToCustomerDTO(customer);
        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getLastName(), result.getLastName());
        assertEquals(customer.getCustomer_url(), result.getCustomer_url());
    }
}