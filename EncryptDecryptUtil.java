package com.webservices.crypto.util;

import com.google.inject.Inject;
import com.webservices.crypto.config.CryptoConfiguration;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Utility class used for encryption and decryption
 * Implements AES Algorithm (symmetric cryptography)
 */
public class EncryptDecryptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptDecryptUtil.class);

    private static final Integer SALT_SIZE = 20;

    private static final String secretKey_algorithm = "PBKDF2WithHmacSHA256";

    private static final String transformation = "AES/CBC/PKCS5PADDING";

    private static final String algorithm = "AES";

    /**
     * Generate the salt
     */
    private static byte[] salt;
    static {
        salt = new byte[SALT_SIZE];
        //SecureRandom secureRandom = new SecureRandom();
        //secureRandom.nextBytes(salt);
    }

    /**
     * Get SecretKeySpec
     * @param secretKey
     * @return
     */
    private static SecretKeySpec getSecretKeySpec(String secretKey) {
        SecretKeyFactory factory;
        SecretKeySpec secretKeySpec = null;
        try {
            factory = SecretKeyFactory.getInstance(secretKey_algorithm);
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt , 65536, 256);
            SecretKey sck = factory.generateSecret(spec);
            secretKeySpec = new SecretKeySpec(sck.getEncoded(), algorithm);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("Exception= in getSecretKeySpec=="+e);
        }
        return secretKeySpec;
    }

    /**
     * Generate the cipher
     */
    private static Cipher cipher;
    static {
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOGGER.error("Exception in cipher==="+e);
        }
    }

    /**
     * Generate IvParameterSpec
     */
    private static IvParameterSpec ivspec;
    static {
        byte[] iv = new byte[16];
        //SecureRandom secureRandom = new SecureRandom() ;
        //secureRandom.nextBytes(iv);
        ivspec = new IvParameterSpec(iv);
    }

    /**
     * Encrypt the value using secret key provided
     * @param strToEncrypt
     * @param secretKey
     * @return encrypted string
     * @throws Exception
     */
    public static String encrypt(String strToEncrypt, String secretKey) throws Exception
    {
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(secretKey), ivspec);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            LOGGER.error("Exception in encrypt==="+e);
            throw e;
        }
    }

    /**
     * Decrypt the encrypted value using secretKey
     * @param strToDecrypt
     * @param secretKey
     * @return decrypted string
     * @throws Exception
     */
    public static String decrypt(String strToDecrypt, String secretKey) throws Exception{
        try
        {
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(secretKey), ivspec);
            return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
        }
        catch (Exception e) {
            LOGGER.error("Exception in decrypt==="+e);
            e.printStackTrace();
            throw e;
        }
    }
}
