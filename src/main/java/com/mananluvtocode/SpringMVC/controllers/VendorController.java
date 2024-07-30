package com.mananluvtocode.SpringMVC.controllers;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.api.model.VendorListDTO;
import com.mananluvtocode.SpringMVC.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendors/")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        //return new ResponseEntity<VendorDTO>(vendorService.createNewVendor(vendorDTO), HttpStatus.CREATED);
        return vendorService.createNewVendor(vendorDTO);
    }

    // get product by id for doing the further work.
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        // return new ResponseEntity<>(vendorService.findById(id), HttpStatus.OK);
        return vendorService.findById(id);
    }


    // for updating the API for doing the further work.
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        // return new ResponseEntity<VendorDTO>(vendorService.updateVendor(id, vendorDTO), HttpStatus.OK);
        return vendorService.updateVendor(id, vendorDTO);
    }

    // for deleting the values for doing the work
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
        return "Deleted Vendor:- " + id;
    }

    // patch the vendor for updating the particular part and doesn't effect the whole object.
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        System.out.println(vendorDTO);
        // return new ResponseEntity<>(vendorService.patchVendor(id, vendorDTO), HttpStatus.OK);
        return vendorService.patchVendor(id, vendorDTO);
    }
}
