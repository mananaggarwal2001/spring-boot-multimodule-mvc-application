package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.api.model.VendorListDTO;
import com.mananluvtocode.SpringMVC.domain.Vendors;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();

    VendorDTO findById(Long id);

    VendorDTO findByName(String name);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
}
