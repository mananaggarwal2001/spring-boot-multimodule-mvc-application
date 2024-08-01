package com.mananluvtocode.SpringMVC.services;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.api.model.VendorListDTO;
import com.mananluvtocode.SpringMVC.domain.Vendors;
import com.mananluvtocode.SpringMVC.mapper.VenderMapper;
import com.mananluvtocode.SpringMVC.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {
    VendorRepository vendorRepository;
    VenderMapper venderMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VenderMapper venderMapper) {
        this.vendorRepository = vendorRepository;
        this.venderMapper = venderMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository
                .findAll()
                .stream()
                .map(venderMapper::vendorToVendorDTO)
                .toList();
    }

    @Override
    public VendorDTO findById(Long id) {
        Optional<Vendors> findVendor = vendorRepository.findById(id);
        Vendors vendors;
        if (findVendor.isPresent()) {
            vendors = findVendor.get();
            return venderMapper.vendorToVendorDTO(vendors);
        }
        return null;
    }

    @Override
    public VendorDTO findByName(String name) {
        return venderMapper.vendorToVendorDTO(vendorRepository.findByName(name));
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendors resultVendor = vendorRepository.save(venderMapper.vendorDTOToVendor(vendorDTO));
        resultVendor.setSelf_link("/api/vendors/" + resultVendor.getId());
        return venderMapper.vendorToVendorDTO(resultVendor);
    }

    @Override
    public void deleteVendorById(Long id) {
        Vendors vendors = vendorRepository.getReferenceById(id);
        vendorRepository.delete(vendors);
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        vendorDTO.setId(id);
        Vendors vendors = venderMapper.vendorDTOToVendor(vendorDTO);
        vendorRepository.save(vendors);
        vendors.setSelf_link("/api/vendors/" + id);
        return venderMapper.vendorToVendorDTO(vendors);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        Optional<VendorDTO> convertedVendor = vendorRepository.findById(id).map(vendorElement -> {
            System.out.println(vendorElement + " from the service layer. ");
            if (vendorDTO.getName() != null) {
                vendorElement.setName(vendorDTO.getName());
            }
            if (vendorDTO.getSelf_link() != null) {
                vendorElement.setSelf_link(vendorDTO.getSelf_link());
            } else {
                vendorElement.setSelf_link("/api/vendors/" + vendorElement.getId());
            }
            return venderMapper.vendorToVendorDTO(vendorRepository.save(vendorElement));
        });
        return convertedVendor.orElseThrow(ResourceNotFoundException::new);
    }
}
