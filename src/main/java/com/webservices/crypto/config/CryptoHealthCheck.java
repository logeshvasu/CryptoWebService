package com.webservices.crypto.config;

import com.codahale.metrics.health.HealthCheck;

/**
 * Crypto Application health check
 */
public class CryptoHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
