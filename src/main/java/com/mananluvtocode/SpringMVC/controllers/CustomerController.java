package com.mananluvtocode.SpringMVC.controllers;

import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.api.model.CustomerListDTO;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers/";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CustomerListDTO getAllCustomers() {
        // return new ResponseEntity<>(new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("{firstName}")
    @ResponseStatus(HttpStatus.OK)
    CustomerDTO getCustomerByFirstName(@PathVariable String firstName) {
        // return new ResponseEntity<>(customerService.getCustomerByFirstName(firstName), HttpStatus.OK);
        return customerService.getCustomerByFirstName(firstName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        System.out.println(customerDTO);
        // return new ResponseEntity<>(customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        // return new ResponseEntity<>(customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    CustomerDTO updatePatchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        // return new ResponseEntity<>(customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        // return new ResponseEntity<String>("Deleted Customer id :- " + id, HttpStatus.OK);
        return "Customer with id " + id + " was deleted";
    }
}