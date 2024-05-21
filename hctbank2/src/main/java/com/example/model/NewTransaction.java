package com.example.model;

import java.util.UUID;

public class NewTransaction {
    private UUID acc_id;
    private UUID to_acc_id;
    private String type;
    public void setAcc_id(UUID acc_id) {
		this.acc_id = acc_id;
	}

	public void setTo_acc_id(UUID to_acc_id) {
		this.to_acc_id = to_acc_id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	private double amount;

    public NewTransaction(UUID acc_id, UUID to_acc_id, String type, double amount) {
        this.acc_id = acc_id;
        this.to_acc_id = to_acc_id;
        this.type = type;
        this.amount = amount;
    }

    public UUID getAcc_id() {
        return acc_id;
    }
    public UUID getTo_acc_id() {
        return to_acc_id;
    }
    public String getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }

}
