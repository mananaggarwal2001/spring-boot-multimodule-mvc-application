package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.controllers.CustomerController;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.mapper.CustomerMapper;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    // for fetching the data from the database or to communicate with the database directly.
    private final CustomerRepository customerRepository;

    // for changing the DTO class to the original class while the program is running
    private final CustomerMapper customerMapper;

    // for defining the common url we have this function for handling the setting the url which is :-
    private String customerUrlSetter(Long id) {
        return CustomerController.BASE_URL + id;
    }

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
        Customer customer = customerRepository.findByFirstName(firstName);
        if (customer == null) {
            throw new ResourceNotFoundException();
        }
        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        savedCustomer.setCustomer_url(customerUrlSetter(savedCustomer.getId()));
        System.out.println(savedCustomer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        customer.setCustomer_url(customerUrlSetter(customer.getId()));
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    // In this function patchCustomer we will update the required fields that are send by the user rest we will leave adjatise as they are and we don't touch those fields.
    // In this function we will target only the certain fields for updating those fields only rest we will not touch those fields and we will leave them adjatise.
    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customerElement -> {
            if (customerElement.getFirstName() != null && (customerDTO.getFirstName() != null)) {
                customerElement.setFirstName(customerDTO.getFirstName());
            }
            if (customerElement.getLastName() != null && customerDTO.getLastName() != null) {
                customerElement.setLastName(customerDTO.getLastName());
            }
            CustomerDTO resultDTOClass = customerMapper.customerToCustomerDTO(customerElement);
            resultDTOClass.setCustomer_url(customerUrlSetter(customerElement.getId()));
            return resultDTOClass;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        Customer resultCustomer = null;
        if (foundCustomer.isPresent()) {
            resultCustomer = foundCustomer.get();
            customerRepository.delete(resultCustomer);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
