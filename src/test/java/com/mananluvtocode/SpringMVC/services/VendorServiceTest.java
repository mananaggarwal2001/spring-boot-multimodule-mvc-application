package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.domain.Vendors;
import com.mananluvtocode.SpringMVC.mapper.VenderMapper;
import com.mananluvtocode.SpringMVC.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VendorServiceTest {
    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VenderMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        List<Vendors> vendorsList = Arrays.asList(new Vendors(), new Vendors(), new Vendors());
        when(vendorRepository.findAll()).thenReturn(vendorsList);
        List<VendorDTO> vendorDTOList = vendorService.getAllVendors();
        assertNotNull(vendorDTOList);
        assertEquals(3, vendorDTOList.size());
    }

    @Test
    void findById() {
        Vendors vendors = new Vendors();
        vendors.setId(1L);
        vendors.setName("VendorName");
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendors));
        VendorDTO resultantVendor = vendorService.findById(1L);
        assertNotNull(resultantVendor);
        assertEquals(1L, resultantVendor.getId());
        assertEquals(vendors.getName(), resultantVendor.getName());
    }

    @Test
    void findByName() {
        Vendors vendors = new Vendors();
        vendors.setName("film");
        when(vendorRepository.findByName(anyString())).thenReturn(vendors);
        VendorDTO resultantVendor = vendorService.findByName("film");
        assertNotNull(resultantVendor);
        assertEquals(resultantVendor.getName(), vendors.getName());
    }

    @Test
    void createNewVendor() {
        Vendors vendors = new Vendors();
        vendors.setName("new");
        vendors.setId(1L);
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(vendors.getName());
        vendorDTO.setId(vendors.getId());
        when(vendorRepository.save(any(Vendors.class))).thenReturn(vendors);
        VendorDTO resultantVendor = vendorService.createNewVendor(vendorDTO);
        assertEquals(resultantVendor.getName(), vendors.getName());
        assertEquals(resultantVendor.getId(), vendors.getId());
    }

    @Test
    void testDeleteVendorById() {
        vendorService.deleteVendorById(anyLong());
        verify(vendorRepository).delete(any());
    }

    @Test
    void updateVendor() {
        Vendors vendors = new Vendors();
        vendors.setId(1L);
        vendors.setName("Manan");

        Vendors returnedVendor = new Vendors();
        returnedVendor.setId(1L);
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("This is my new Vendor");
        when(vendorRepository.save(vendors)).thenReturn(returnedVendor);
        VendorDTO result = vendorService.updateVendor(1L, vendorDTO);
        assertNotNull(result);
        assertEquals(result.getSelf_link(), "/api/vendors/1");
        assertEquals(result.getName(), vendorDTO.getName());

    }
}