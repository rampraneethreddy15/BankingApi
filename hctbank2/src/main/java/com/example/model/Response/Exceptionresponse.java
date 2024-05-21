package com.example.model.Response;

import lombok.Data;
import lombok.Getter;


public class Exceptionresponse {
    @Getter
    private String message;
    @Getter
    private String code;

    public Exceptionresponse(String message, String code) {
        this();
        this.message = message;
        this.code = code;
    }

    public Exceptionresponse() {
    }
}
