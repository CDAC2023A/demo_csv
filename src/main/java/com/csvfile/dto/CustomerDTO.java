package com.csvfile.dto;

import lombok.Getter;
import lombok.Setter;


public class CustomerDTO {
    private String customerId;
    private String name;
    private String email;
    {
        this.customerId = customerId;
    }
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
