package com.example.model.Response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionResponse {
    private String message;
    private String statusCode;
    private UUID ref_id;

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public UUID getRef_id() {
		return ref_id;
	}

	public void setRef_id(UUID ref_id) {
		this.ref_id = ref_id;
	}

	@JsonCreator
    public TransactionResponse(@JsonProperty("message") String message,
                               @JsonProperty("statusCode") String statusCode,
                               @JsonProperty("ref_id") UUID ref_id) {
        this.message = message;
        this.statusCode = statusCode;
        this.ref_id = ref_id;
    }
}
