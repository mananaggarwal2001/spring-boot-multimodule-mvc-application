package com.mananluvtocode.SpringMVC.bootstrap;

import com.mananluvtocode.SpringMVC.domain.Category;
import com.mananluvtocode.SpringMVC.domain.Customer;
import com.mananluvtocode.SpringMVC.repositories.CategoryRepository;
import com.mananluvtocode.SpringMVC.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// this commandline runner will run on the spring boot specific application not outside the spring boot application.
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final List<Category> categoryList = new ArrayList<>();
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category dried = new Category();
        dried.setName("Dried");
        Category wet = new Category();
        wet.setName("Wet");
        Category fruits = new Category();
        fruits.setName("Fruits");
        Category exotic = new Category();
        exotic.setName("Exotic");
        Category nuts = new Category();
        nuts.setName("Nuts");
        categoryList.add(dried);
        categoryList.add(wet);
        categoryList.add(fruits);
        categoryList.add(exotic);
        categoryList.add(nuts);

        categoryRepository.saveAll(categoryList);
        System.out.println("Data loaded successfully and count is :- " + categoryRepository.count());


        // For the Customer Class we have these classes that will be loaded into the database
        List<Customer> customerList = new ArrayList<>();
        Customer customer= new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setCustomer_url("https://www.google.com");
        customerList.add(customer);

        Customer customer1= new Customer();
        customer1.setFirstName("Jane");
        customer1.setLastName("Doe");
        customer1.setCustomer_url("https://www.google.com");
        customerList.add(customer1);

        Customer customer2= new Customer();
        customer2.setFirstName("Bob");
        customer2.setLastName("Smith");
        customer2.setCustomer_url("https://www.google.com");
        customerList.add(customer2);

        customerRepository.saveAll(customerList);
        System.out.println("Data loaded successfully and count is :- " + customerRepository.count());

    }
}
