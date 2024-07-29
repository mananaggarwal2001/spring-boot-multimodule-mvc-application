package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.CustomerDTO;
import com.mananluvtocode.SpringMVC.bootstrap.Bootstrap;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.mapper.CustomerMapper;
import com.mananluvtocode.SpringMVC.repositories.CategoryRepository;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceImplITTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());
        // setup the data for testing for doing the work.
        // this will load all the data for us before doing the actual testing on that data.
        // This is initiated because everytime the data jpa is being cleared after running the tests on the Database itself so this method is being put for reinitializing the data into the database.
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();
        System.out.println("Data Loaded Successfully");
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchCustomerUpdateFirstName() {
        String updatedName = "UpdateName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getReferenceById(id);
        System.out.println("Original Customer is :- " + originalCustomer);
        assertNotNull(originalCustomer);
        // extract the particular fields from the customer object which is extracted from this customer.
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id, customerDTO);
        Customer UpdatedCustomer = customerRepository.findById(id).get();
        System.out.println(UpdatedCustomer);
        assertNotNull(UpdatedCustomer);
        assertEquals(updatedName, UpdatedCustomer.getFirstName());
        assertThat(originalFirstName).isNotEqualTo(UpdatedCustomer.getFirstName());
        assertThat(originalLastName).isEqualTo(UpdatedCustomer.getLastName());
    }

    @Test
    void patchCustomerUpdateLastName() {
        String updatedName = "UpdateLastName";
        Long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.getReferenceById(id);
        System.out.println("Original Customer is :- " + originalCustomer);
        assertNotNull(originalCustomer);
        String originalLastName = originalCustomer.getLastName();
        String originalFirstName = originalCustomer.getFirstName();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);
        customerService.patchCustomer(id, customerDTO);
        Customer UpdatedCustomer = customerRepository.findById(id).get();
        System.out.println(UpdatedCustomer);
        assertNotNull(UpdatedCustomer);
        assertEquals(updatedName, UpdatedCustomer.getLastName());
        assertThat(originalLastName).isNotEqualTo(UpdatedCustomer.getLastName());
        assertThat(originalFirstName).isEqualTo(UpdatedCustomer.getFirstName());
    }

    private Long getCustomerIdValue() {
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("Customer Found: " + customerList.size());
        return customerList.get(0).getId();
    }
}
