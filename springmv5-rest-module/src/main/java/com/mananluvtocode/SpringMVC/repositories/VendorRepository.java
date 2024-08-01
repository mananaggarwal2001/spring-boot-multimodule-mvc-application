package com.mananluvtocode.SpringMVC.repositories;

import com.mananluvtocode.SpringMVC.domain.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorRepository extends JpaRepository<Vendors, Long> {
    Vendors findByName(String name);
}