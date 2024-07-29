package com.mananluvtocode.SpringMVC.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import com.mananluvtocode.SpringMVC.services.CustomerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<CustomerDTO> customerList = Arrays.asList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerList);
        mockMvc.perform(get("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    void getCustomerByFirstName() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        when(customerService.getCustomerByFirstName("John")).thenReturn(customerDTO);
        mockMvc.perform(get("/api/v1/customers/John").contentType(MediaType.APPLICATION_JSON))
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
        returnDTO.setCustomer_url("/api/v1/customers/1");
        returnDTO.setId(1L);

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
//                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
//                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
//                .andExpect(jsonPath("$.customer_url").value(returnDTO.getCustomer_url()));

        String response = mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON).content(finalvalue))
                .andReturn().getResponse().getContentAsString();

        System.out.println("The response of this request is :- " + response);

    }

    // for checking for the update operation
    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Flinder Super Doe");
        customerDTO.setLastName("Doe");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomer_url("/api/v1/customers/1");

        String finalvalue = mapper.writeValueAsString(customerDTO);
        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
        String responseResult = mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(finalvalue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(customerDTO.getFirstName()))
                .andExpect(jsonPath("$.customer_url").value("/api/v1/customers/1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseResult);

    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Manan");
        customerDTO.setLastName("Aggarwal");
        CustomerDTO returnedCustomer = new CustomerDTO();
        returnedCustomer.setFirstName(customerDTO.getFirstName());
        returnedCustomer.setLastName(customerDTO.getLastName());
        returnedCustomer.setCustomer_url("/api/v1/customers/1");
        returnedCustomer.setId(1L);
        String resultMapper = mapper.writeValueAsString(customerDTO);
        when(customerService.patchCustomer(1L, customerDTO)).thenReturn(returnedCustomer);

        String responseString = mockMvc.perform(patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON).content(resultMapper))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(returnedCustomer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(returnedCustomer.getLastName()))
                .andExpect(jsonPath("$.customer_url").value(returnedCustomer.getCustomer_url()))
                .andReturn().getResponse().getContentAsString();
        System.out.println("The Response of this Request is:- ");
        System.out.println(responseString);
    }

    @Test
    void DeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(anyLong());
    }
}