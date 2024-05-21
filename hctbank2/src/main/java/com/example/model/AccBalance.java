package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accbalance")
public class AccBalance {

    @Id
    UUID accId;
    double balance;
	public AccBalance(UUID id, double d) {
		// TODO Auto-generated constructor stub
		this.accId = id;
		this.balance = d;
	}
	public AccBalance() {
		// TODO Auto-generated constructor stub
	}
	public UUID getAccId() {
		return accId;
	}
	public void setAccId(UUID accId) {
		this.accId = accId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}



}