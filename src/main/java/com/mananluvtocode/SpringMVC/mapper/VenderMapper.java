package com.mananluvtocode.SpringMVC.mapper;

import com.mananluvtocode.SpringMVC.api.model.VendorDTO;
import com.mananluvtocode.SpringMVC.domain.Vendors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VenderMapper {
    VenderMapper INSTANCE = Mappers.getMapper(VenderMapper.class);

    VendorDTO vendorToVendorDTO(Vendors vendors);

    Vendors vendorDTOToVendor(VendorDTO vendorDTO);
}
