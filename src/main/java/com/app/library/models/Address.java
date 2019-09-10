package com.app.library.models;

import lombok.Data;

@Data
public class Address {
    private String countryISO;
    private int postCode;
    private int streetNumber;
    private String streetName;
}
