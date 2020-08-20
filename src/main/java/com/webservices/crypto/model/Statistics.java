package com.webservices.crypto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Statistics Domain Object
 * Contains mean, standard deviation
 */
public class Statistics implements Serializable {

    private String mean ;

    private String standardDeviation;

    public Statistics() {

    }

    public Statistics(String mean, String standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    @JsonProperty
    public String getMean() {
        return mean;
    }

    @JsonProperty
    public String getStandardDeviation() {
        return standardDeviation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Statistics{");
        sb.append("mean='").append(mean).append('\'');
        sb.append(", standardDeviation='").append(standardDeviation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

