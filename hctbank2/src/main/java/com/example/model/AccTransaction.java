package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "acctransaction")
public class AccTransaction {
    @Id
    private UUID trans_id;
    private UUID trans_refid;

    private UUID acc_id;
    private  double credit;
    private  double debit;
    private  double avlbalance;
    private Timestamp lastupdated;

    public UUID getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(UUID trans_id) {
		this.trans_id = trans_id;
	}

	public UUID getTrans_refid() {
		return trans_refid;
	}

	public void setTrans_refid(UUID trans_refid) {
		this.trans_refid = trans_refid;
	}

	public UUID getAcc_id() {
		return acc_id;
	}

	public void setAcc_id(UUID acc_id) {
		this.acc_id = acc_id;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getAvlbalance() {
		return avlbalance;
	}

	public void setAvlbalance(double avlbalance) {
		this.avlbalance = avlbalance;
	}

	public Timestamp getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Timestamp lastupdated) {
		this.lastupdated = lastupdated;
	}

	public AccTransaction(@JsonProperty("trans_id") UUID trans_id,
                          @JsonProperty("trans_refid") UUID trans_refid,
                          @JsonProperty("acc_id") UUID acc_id,
                          @JsonProperty("credit") double credit,
                          @JsonProperty("debit") double debit,
                          @JsonProperty("avlbalance") double avlbalance,
                          @JsonProperty("lastupdated") Timestamp lastupdated) {
        this();
        this.trans_id = trans_id;
        this.trans_refid = trans_refid;
        this.acc_id = acc_id;
        this.credit = credit;
        this.debit = debit;
        this.avlbalance = avlbalance;
        this.lastupdated = lastupdated;
    }

    public AccTransaction() {

    }
}
