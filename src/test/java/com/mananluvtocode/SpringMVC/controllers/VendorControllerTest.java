package com.mananluvtocode.SpringMVC.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.domain.Vendors;
import com.mananluvtocode.SpringMVC.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {
    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController vendorController;
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityHandler())
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        List<VendorDTO> vendorDTOList = Arrays.asList(new VendorDTO(), new VendorDTO(), new VendorDTO());
        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);
        mockMvc.perform(get("/api/v1/vendors/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
    }

    @Test
    void createNewVendor() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Vendor");
        vendor.setId(1L);
        String resultObject = objectMapper.writeValueAsString(vendor);

        VendorDTO returnedVendor = new VendorDTO();
        returnedVendor.setName(vendor.getName());
        returnedVendor.setId(vendor.getId());
        returnedVendor.setSelf_link("/api/vendors/" + returnedVendor.getId());

        when(vendorService.createNewVendor(vendor)).thenReturn(returnedVendor);
        String result = mockMvc.perform(post("/api/v1/vendors/").contentType(MediaType.APPLICATION_JSON)
                        .content(resultObject))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    void testUpdateVendor() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setId(1L);
        vendor.setName("Vendor");
        VendorDTO returnedVendor = new VendorDTO();
        returnedVendor.setId(vendor.getId());
        returnedVendor.setName(vendor.getName());
        returnedVendor.setSelf_link("/api/vendors/" + returnedVendor.getId());
        String resultObject = objectMapper.writeValueAsString(vendor);
        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnedVendor);
        String returnedResponse = mockMvc.perform(put("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON).content(resultObject))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(vendor.getName()))
                .andExpect(jsonPath("$.self_link").value("/api/vendors/1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(returnedResponse);
    }

    @Test
    void testDeleteVendor() throws Exception {
        mockMvc.perform(delete("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testPatchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Manan");
        VendorDTO resultantVendor = new VendorDTO();
        resultantVendor.setName(vendorDTO.getName());
        String vendorString = objectMapper.writeValueAsString(vendorDTO);
        resultantVendor.setId(1L);
        resultantVendor.setName(vendorDTO.getName());
        resultantVendor.setSelf_link("/api/vendors/" + resultantVendor.getId());
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(resultantVendor);
        String result = mockMvc.perform(patch("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON)
                        .content(vendorString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(vendorDTO.getName()))
                .andExpect(jsonPath("$.self_link").value("/api/vendors/1"))
                .andExpect(jsonPath("$.id").value(resultantVendor.getId()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}