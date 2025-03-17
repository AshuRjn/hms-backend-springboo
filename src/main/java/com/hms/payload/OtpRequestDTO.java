package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequestDTO {
    private String phoneNumber;
    private String otp;
}
