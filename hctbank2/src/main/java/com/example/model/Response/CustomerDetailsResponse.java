package com.example.model.Response;


import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;


 public class CustomerDetailsResponse {
    @Getter
    private String name;

     private long phone;
    private String email;
    private UUID cust_id;
    @Getter
    private UUID acc_id;
    private Timestamp created_on;

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

	public UUID getCust_id() {
		return cust_id;
	}

	public void setCust_id(UUID cust_id) {
		this.cust_id = cust_id;
	}

	public UUID getAcc_id() {
		return acc_id;
	}

	public void setAcc_id(UUID acc_id) {
		this.acc_id = acc_id;
	}

	public Timestamp getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}

	public CustomerDetailsResponse() {

    }

    public CustomerDetailsResponse(String name, long phone, String email, UUID cust_id, UUID acc_id, Timestamp created_on) {
        this();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cust_id = cust_id;
        this.acc_id = acc_id;
        this.created_on = created_on;
    }
}
