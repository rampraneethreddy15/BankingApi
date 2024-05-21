package com.example.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="custaddress")
public class CustAddress {
    @Id
    private UUID address_id;
    @Getter
    private String country;
    private String city;

    private String addresslane;
    private long pin;
    private Timestamp lastupdate;


    public UUID getAddress_id() {
		return address_id;
	}


	public void setAddress_id(UUID address_id) {
		this.address_id = address_id;
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


	public Timestamp getLastupdate() {
		return lastupdate;
	}


	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}


	public CustAddress(UUID address_id, String country, String city, String addresslane, long pin, Timestamp lastupdate) {
        this();
        this.address_id = address_id;
        this.country = country;
        this.city = city;
        this.addresslane = addresslane;
        this.pin = pin;
        this.lastupdate = lastupdate;
    }


    public CustAddress() {

    }
}
