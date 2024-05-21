package com.example.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Data
@Getter
@Setter
@Table(name = "custdetails")
public class CustDetails {
    @Id
    private UUID cust_id;

    private String name;
    private long phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private CustAddress address;

    private String email;
    private Timestamp Created;
    private Timestamp Updated;

    public UUID getCust_id() {
		return cust_id;
	}

	public void setCust_id(UUID cust_id) {
		this.cust_id = cust_id;
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

	public CustAddress getAddress() {
		return address;
	}

	public void setAddress(CustAddress address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreated() {
		return Created;
	}

	public void setCreated(Timestamp created) {
		Created = created;
	}

	public Timestamp getUpdated() {
		return Updated;
	}

	public void setUpdated(Timestamp updated) {
		Updated = updated;
	}

	public CustDetails(UUID cust_id, String name, long phone, String email, Timestamp created, Timestamp updated) {
        this();
        this.cust_id = cust_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        Created = created;
        Updated = updated;
    }

    public CustDetails(UUID cust_id, String name, long phone, CustAddress address, String email, Timestamp created, Timestamp updated) {
        this.cust_id = cust_id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        Created = created;
        Updated = updated;
    }

    public CustDetails() {

    }
}
