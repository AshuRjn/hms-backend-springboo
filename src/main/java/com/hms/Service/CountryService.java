package com.hms.Service;

import com.hms.entity.Country;
import com.hms.payload.CountryDTO;
import com.hms.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    CountryDTO mapToDTO (Country country){
        return modelMapper.map(country, CountryDTO.class);
    }
    Country mapToEntity (CountryDTO countryDto){
        return modelMapper.map(countryDto, Country.class);
    }

    //ADD Country
    public CountryDTO addCountry(CountryDTO countryDto) {
        Country country = mapToEntity(countryDto);
        Optional<Country> byName = countryRepository.findByCountryName(country.getCountryName());
        if (byName.isPresent()){
            throw new RuntimeException("Country already exists");
        }else {
            Country save = countryRepository.save(country);
            return mapToDTO(save);
        }

    }
    // delete all associated field or data
    @Transactional
    public void deleteCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country With id" +id+ " not found"));

        countryRepository.delete(country);

    }

    public List<CountryDTO> getAllCountry() {
        List<Country> countryList = countryRepository.findAll();
        return countryList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
