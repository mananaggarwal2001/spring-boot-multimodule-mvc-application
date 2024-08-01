package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.CustomerDTO;
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
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setId(2L);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedCustomer = customerService.createNewCustomer(customerDTO);
        assertNotNull(savedCustomer);
        assertEquals(savedCustomer.getFirstName(), customerDTO.getFirstName());
        assertEquals(savedCustomer.getLastName(), customerDTO.getLastName());
    }

    @Test
    void saveCustomerByDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jammy Singh Body balia");

        Customer resultCustomer = new Customer();
        resultCustomer.setFirstName(customerDTO.getFirstName());
        resultCustomer.setLastName(customerDTO.getLastName());
        resultCustomer.setId(2L);

        when(customerRepository.save(any(Customer.class))).thenReturn(resultCustomer);
        CustomerDTO resultantCustomer = customerService.saveCustomerByDTO(2L, customerDTO);
        assertNotNull(resultantCustomer);
        assertEquals(resultantCustomer.getFirstName(), customerDTO.getFirstName());

    }
}