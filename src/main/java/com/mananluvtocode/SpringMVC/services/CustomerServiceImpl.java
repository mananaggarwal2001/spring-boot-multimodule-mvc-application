package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.mapper.CustomerMapper;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    // for fetching the data from the database or to communicate with the database directly.
    private final CustomerRepository customerRepository;

    // for changing the DTO class to the original class while the program is running
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerByFirstName(String firstName) {
        return customerMapper
                .customerToCustomerDTO(customerRepository
                        .findByFirstName(firstName));
    }
}
