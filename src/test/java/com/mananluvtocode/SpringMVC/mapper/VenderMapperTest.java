package com.mananluvtocode.SpringMVC.mapper;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.domain.Vendors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VenderMapperTest {

    VenderMapper mapper = VenderMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendors vendors = new Vendors();
        vendors.setId(1L);
        vendors.setSelf_link("/api/vendors/1");
        vendors.setName("Vendors");
        VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendors);
        assertNotNull(vendorDTO);
        assertEquals(vendorDTO.getId(), vendors.getId());
        assertEquals(vendorDTO.getName(), vendors.getName());
        assertEquals(vendorDTO.getSelf_link(), vendors.getSelf_link());
    }

    @Test
    void vendorDTOToVendor() {
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("VendorDTO");
        vendorDTO.setSelf_link("/api/vendors/1");
        Vendors vendors = mapper.vendorDTOToVendor(vendorDTO);
        assertNotNull(vendors);
        assertEquals(vendorDTO.getId(), vendors.getId());
        assertEquals(vendorDTO.getName(), vendors.getName());
        assertEquals(vendorDTO.getSelf_link(), vendors.getSelf_link());
    }
}