package com.hms.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class TokenDTO {
    private String token;
    private String type;
}
