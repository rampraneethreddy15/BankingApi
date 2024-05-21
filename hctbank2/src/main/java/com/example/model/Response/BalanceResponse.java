package com.example.model.Response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;


public class BalanceResponse {
    @Getter
    private UUID acc_id;

    @Getter
    private double avlbalance;

    public UUID getAcc_id() {
		return acc_id;
	}

	public void setAcc_id(UUID acc_id) {
		this.acc_id = acc_id;
	}

	public double getAvlbalance() {
		return avlbalance;
	}

	public void setAvlbalance(double avlbalance) {
		this.avlbalance = avlbalance;
	}

	@JsonCreator
    public BalanceResponse(@JsonProperty("acc_id") UUID acc_id,
                           @JsonProperty("avlbalance") double avlbalance) {
        this.acc_id = acc_id;
        this.avlbalance = avlbalance;
    }
}
