package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.mapper.CustomerMapper;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        // this is used for returning the real objects.
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
        assertEquals(3, customerDTOs.size());
        assertNotNull(customerDTOs);
    }

    @Test
    void getCustomerByFirstName() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        when(customerRepository.findByFirstName("John")).thenReturn(customer);
        CustomerDTO customerDTO = customerService.getCustomerByFirstName("John");
        assertNotNull(customerDTO);
    }
}