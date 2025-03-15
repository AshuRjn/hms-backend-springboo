package com.hms.Service;

import com.hms.entity.*;
import com.hms.payload.PropertyDTO;
import com.hms.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private  final PropertyRepository propertyRepository;
    private  final CountryRepository countryRepository;
    private  final CityRepository cityRepository;

    private  final ZipCodeRepository zipCodeRepository;
    private  final ModelMapper modelMapper;
    public PropertyService(PropertyRepository propertyRepository,
                           CountryRepository countryRepository,
                           CityRepository cityRepository,
                           ZipCodeRepository zipCodeRepository,
                           ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.zipCodeRepository = zipCodeRepository;
        this.modelMapper = modelMapper;
    }

    Property mapToEntity(PropertyDTO propertyDTO){
        return modelMapper.map(propertyDTO, Property.class);
    }
    PropertyDTO mapToDTO(Property property){
        return modelMapper.map(property, PropertyDTO.class);
    }

 // ADD Property
    public PropertyDTO addProperty(PropertyDTO propertyDTO) {

        boolean exists = propertyRepository.existsByNameAndCityIdAndCountryId(
                propertyDTO.getName(), propertyDTO.getCityId(), propertyDTO.getCountryId());

        if (exists) {
            throw new RuntimeException("Property already exists with name: " + propertyDTO.getName() +
                    " in city ID: " + propertyDTO.getCityId() +
                    " and country ID: " + propertyDTO.getCountryId());
        }

        Property property = mapToEntity(propertyDTO);
        // check if property is already exists

        City city = cityRepository.findById(propertyDTO.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with ID: " + propertyDTO.getCityId()));

        Country country = countryRepository.findById(propertyDTO.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found with ID: " + propertyDTO.getCountryId()));

        ZipCode zipcode = zipCodeRepository.findById(propertyDTO.getZipId())
                .orElseThrow(() -> new RuntimeException("Zipcode not found with ID: " + propertyDTO.getZipId()));
        // Set the existing city and country

        property.setCity(city);
        property.setCountry(country);
        property.setZipCode(zipcode);

        Property save = propertyRepository.save(property);
        return mapToDTO(save);
    }

    // delete property
    @Transactional
    public void deleteProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property  with id " + id + "not found"));

        propertyRepository.delete(property);
    }

    public List<PropertyDTO> getAllProperty() {
        List<Property> propertyList = propertyRepository.findAll();

        return propertyList.stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public PropertyDTO getPropertyById(Long id) {
        Optional<Property> byId = propertyRepository.findById(id);
        if (byId.isPresent()){
            Property property = byId.get();
            return mapToDTO(property);
        }
        return null;
    }

    @Transactional
    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO) {

        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with :" + id));

        // ✅ Update only primitive fields
        if (propertyDTO.getName() != null) existingProperty.setName(propertyDTO.getName());
        if (propertyDTO.getNoOfGuest() != null) existingProperty.setNoOfGuest(propertyDTO.getNoOfGuest());
        if (propertyDTO.getNoOfRooms() != null) existingProperty.setNoOfRooms(propertyDTO.getNoOfRooms());
        if (propertyDTO.getNoOfBathroom() != null) existingProperty.setNoOfBathroom(propertyDTO.getNoOfBathroom());
        if (propertyDTO.getNoOfBeds() != null) existingProperty.setNoOfBeds(propertyDTO.getNoOfBeds());

        // ✅ Update location references safely
        if (propertyDTO.getCityId() != null) {
            if (existingProperty.getCity() == null || !propertyDTO.getCityId().equals(existingProperty.getCity().getId())) {
                City city = cityRepository.findById(propertyDTO.getCityId())
                        .orElseThrow(() -> new RuntimeException("Invalid city ID: " + propertyDTO.getCityId()));
                existingProperty.setCity(city);
            }
        }

        if (propertyDTO.getCountryId() != null) {
            if (existingProperty.getCountry() == null || !propertyDTO.getCountryId().equals(existingProperty.getCountry().getId())) {
                Country country = countryRepository.findById(propertyDTO.getCountryId())
                        .orElseThrow(() -> new RuntimeException("Invalid country ID: " + propertyDTO.getCountryId()));
                existingProperty.setCountry(country);
            }
        }

        if (propertyDTO.getZipId() != null) {
            if (existingProperty.getZipCode() == null || !propertyDTO.getZipId().equals(existingProperty.getZipCode().getId())) {
                ZipCode zipCode = zipCodeRepository.findById(propertyDTO.getZipId())
                        .orElseThrow(() -> new RuntimeException("Invalid zip code ID: " + propertyDTO.getZipId()));
                existingProperty.setZipCode(zipCode);
            }
        }
        // ✅ Save updated property
        Property saved = propertyRepository.save(existingProperty);

        return mapToDTO(saved);

    }

    public List<PropertyDTO> searchHotels(String name) {
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


}
