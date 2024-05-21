package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailedAddress {
    private String name;
    private long phone;
    //private long address_id;
    private String email;
    private String country;
    private String city;
    private String addresslane;
    private long pin;

    public CustomerDetailedAddress(String string, int i, String string2, String string3, String string4, String string5,
			int j) {
		// TODO Auto-generated constructor stub
    	this.name = string;
    	this.phone = i;
    	this.email = string2;
    	this.country = string3;
    	this.city = string4;
    	this.addresslane = string5;
    	this.pin = j;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddresslane() {
        return addresslane;
    }

    public void setAddresslane(String addresslane) {
        this.addresslane = addresslane;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }
}
