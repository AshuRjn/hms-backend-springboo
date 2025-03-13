package com.hms.Service;

import com.hms.entity.ZipCode;
import com.hms.payload.ZipCodeDTO;
import com.hms.repository.ZipCodeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZipCodeService {

    private final ZipCodeRepository zipCodeRepository;
    private final ModelMapper modelMapper;
    public ZipCodeService(ZipCodeRepository zipCodeRepository, ModelMapper modelMapper) {
        this.zipCodeRepository = zipCodeRepository;
        this.modelMapper = modelMapper;
    }

    ZipCode mapToEntity(ZipCodeDTO zipCodeDTO){
        return modelMapper.map(zipCodeDTO, ZipCode.class);
    }
    ZipCodeDTO mapToDTO(ZipCode zipCode){
        return modelMapper.map(zipCode, ZipCodeDTO.class);
    }

    public ZipCodeDTO addZipCode(ZipCodeDTO zipDTO) {
        ZipCode zipCode = mapToEntity(zipDTO);
        Optional<ZipCode> byZipCode = zipCodeRepository.findByZipCode(zipCode.getZipCode());
        if (byZipCode.isPresent()){
            throw new RuntimeException("ZipCode already exists !");
        }else {
            ZipCode saved = zipCodeRepository.save(zipCode);
            return mapToDTO(saved);
        }
    }
}
