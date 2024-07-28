package com.mananluvtocode.SpringMVC.repositories;

import com.mananluvtocode.SpringMVC.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByFirstName(String firstName);
}
