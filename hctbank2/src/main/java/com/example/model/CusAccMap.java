package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cusaccmap")
@NoArgsConstructor
@AllArgsConstructor
public class CusAccMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private AccBalance accBalance;

    @OneToOne(cascade = CascadeType.ALL)
    private CustDetails custDetails;

	public CusAccMap(int i, AccBalance ac, CustDetails cd) {
		// TODO Auto-generated constructor stub
		this.id = i;
		this.accBalance = ac;
		this.custDetails = cd;
	}

	public CusAccMap() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AccBalance getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(AccBalance accBalance) {
		this.accBalance = accBalance;
	}

	public CustDetails getCustDetails() {
		return custDetails;
	}

	public void setCustDetails(CustDetails custDetails) {
		this.custDetails = custDetails;
	}

}
