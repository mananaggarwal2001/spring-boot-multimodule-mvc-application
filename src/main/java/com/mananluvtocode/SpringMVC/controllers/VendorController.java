package com.mananluvtocode.SpringMVC.controllers;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.api.model.VendorListDTO;
import com.mananluvtocode.SpringMVC.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/vendors/")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<VendorListDTO> getAllVendors() {
        return new ResponseEntity<VendorListDTO>(new VendorListDTO(vendorService.getAllVendors()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) {
        return new ResponseEntity<VendorDTO>(vendorService.createNewVendor(vendorDTO), HttpStatus.CREATED);
    }

    // get product by id for doing the further work.
    @GetMapping("{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable Long id) {
        return new ResponseEntity<>(vendorService.findById(id), HttpStatus.OK);
    }


    // for updating the API for doing the further work.
    @PutMapping("{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return new ResponseEntity<VendorDTO>(vendorService.updateVendor(id, vendorDTO), HttpStatus.OK);
    }

    // for deleting the values for doing the work
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
        return new ResponseEntity<>("Deleted Vendor:- " + id, HttpStatus.OK);
    }

    // patch the vendor for updating the particular part and doesn't effect the whole object.
    @PatchMapping("{id}")
    public ResponseEntity<VendorDTO> patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        System.out.println(vendorDTO);
        return new ResponseEntity<>(vendorService.patchVendor(id, vendorDTO), HttpStatus.OK);
    }
}
