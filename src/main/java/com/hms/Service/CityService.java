package com.hms.Service;

import com.hms.entity.City;
import com.hms.payload.CityDTO;
import com.hms.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    City mapToEntity(CityDTO cityDTO) {
        return modelMapper.map(cityDTO, City.class);
    }

    CityDTO mapToDTO(City city) {
        return modelMapper.map(city, CityDTO.class);
    }

    //ADD City
    public CityDTO addCity(CityDTO cityDTO) {
        City city = mapToEntity(cityDTO);
        Optional<City> byCityName = cityRepository.findByCityName(city.getCityName());
        if (byCityName.isPresent()) {
            throw new RuntimeException("City already exist !");
        } else {
            City save = cityRepository.save(city);
            return mapToDTO(save);
        }

    }

  // delete the city if it's not associated with any properties
    @Transactional
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found !"));

        if (!city.getProperties().isEmpty()) {
            throw new IllegalStateException("Cannot delete city. Properties are still associated with it.");
        }
        cityRepository.delete(city);
    }

}