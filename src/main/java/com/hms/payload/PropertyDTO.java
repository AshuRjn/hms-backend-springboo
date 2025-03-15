package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDTO {
    private Long id;
    private String name;
    private Integer noOfGuest;
    private Integer noOfRooms;
    private Integer noOfBathroom;
    private Integer noOfBeds;
    private Long countryId;
    private Long cityId;
    private Long zipId;
}
