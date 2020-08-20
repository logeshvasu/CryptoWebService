package com.webservices.crypto.service;

import com.webservices.crypto.model.Statistics;

/**
 * CryptoService Interface
 */
public interface CryptoService {

    Statistics pushAndRecalculate(Double num);

    Statistics pushRecalculateAndEncrypt(Double num) throws Exception;

    String decrypt(String encryptedString) throws Exception;
}
