package com.webservices.crypto.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * CryptoConfiguration holds properties
 */
public class CryptoConfiguration extends Configuration {

    @NotEmpty
    private String secretKey;

    @JsonProperty
    public String getSecretKey() {
        return secretKey;
    }

    @JsonProperty
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
