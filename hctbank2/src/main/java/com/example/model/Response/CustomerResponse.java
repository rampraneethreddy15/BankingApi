package com.example.model.Response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;



public class CustomerResponse {

    @Getter
    private String name;
    @Getter
    private UUID cus_id;
    @Getter
    private UUID acc_id;
    private double balance;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getCus_id() {
		return cus_id;
	}

	public void setCus_id(UUID cus_id) {
		this.cus_id = cus_id;
	}

	public UUID getAcc_id() {
		return acc_id;
	}

	public void setAcc_id(UUID acc_id) {
		this.acc_id = acc_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@JsonCreator
    public CustomerResponse(@JsonProperty("name")String name,
                            @JsonProperty("cus_id") UUID cus_id,
                            @JsonProperty("acc_id") UUID acc_id,
                            @JsonProperty("balance") double balance) {
        this.name = name;
        this.cus_id = cus_id;
        this.acc_id = acc_id;
        this.balance = balance;
    }
}
