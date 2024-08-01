package com.mananluvtocode.SpringMVC.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mananluvtocode.CustomerDTO;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import com.mananluvtocode.SpringMVC.services.CustomerService;
import com.mananluvtocode.SpringMVC.services.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;

    // this Object Mapper is used for binding the POJO class to the json as this uses the Jackson binding technique for doing the work.
    private ObjectMapper mapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new RestResponseEntityHandler()).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customerList = Arrays.asList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerList);
        mockMvc.perform(get("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    void getCustomerByFirstName() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        when(customerService.getCustomerByFirstName("John")).thenReturn(customerDTO);
        mockMvc.perform(get("/api/v1/customers/John")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Customer");
        customer.setLastName("Aggarwal");
        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        // object mapper is used for binding the pojo to the json manually as this uses the jakson binding for doing  this work.
        String finalvalue = mapper.writeValueAsString(customer);
        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);
//        mockMvc.perform(post("/api/v1/customers/")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.firstName").value("Customer"))
//                .andExpect(jsonPath("$.lastName").value("Aggarwal"))
//                .andExpect(jsonPath("$.customer_url").value("/api/v1/customers/1"));
//        mockMvc.perform(post("/api/v1/customers/")
//                .contentType(MediaType.APPLICATION_JSON).content(finalvalue))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.firstName").value(customer.getFirstname()))
//                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
//                .andExpect(jsonPath("$.customer_url").value(returnDTO.getCustomer_url()));

        String response = mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON).content(finalvalue).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        System.out.println("The response of this request is :- " + response);

    }

    // for checking for the update operation
    @Test
    void DeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    void resourceNotFoundException() throws Exception {
        when(customerService.getCustomerByFirstName(anyString())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/api/v1/customers/foobar"))
                .andExpect(status().isNotFound());
    }
}