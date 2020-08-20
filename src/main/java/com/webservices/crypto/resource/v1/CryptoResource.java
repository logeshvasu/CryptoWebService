package com.webservices.crypto.resource.v1;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.webservices.crypto.model.Statistics;
import com.webservices.crypto.service.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Crypto Rest controller
 */
@Path("/v1")
public class CryptoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoResource
            .class);

    private final CryptoService cryptoService;

    @Inject
    public CryptoResource(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    /**
     * Post api to pushAndRecalculate
     * Accept application/x-www-form-urlencoded
     * @param number
     * @return statistics object with running mean
     * and standard deviation
     */
    @POST
    @Path("/pushAndRecalculate")
    @Timed
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Statistics pushAndRecalculate(@FormParam("number") String number) {
        LOGGER.info("Request number==="+number);
        LOGGER.info("Calling pushAndRecalculate service");
        return cryptoService.pushAndRecalculate(Double.valueOf(number));
    }

    /**
     * Post api to pushRecalculateAndEncrypt
     * Accept application/x-www-form-urlencoded
     * @param number
     * @return statistics object with encrypted running mean
     * and standard deviation
     */
    @POST
    @Path("/pushRecalculateAndEncrypt")
    @Timed
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Statistics pushRecalculateAndEncrypt(@FormParam("number") String number) {
        try {
            LOGGER.info("Request number==="+number);
            LOGGER.info("Calling pushRecalculateAndEncrypt service");
            return cryptoService.pushRecalculateAndEncrypt(Double.valueOf(number));
        } catch (Exception e) {
            LOGGER.error("Exception in pushRecalculateAndEncrypt ==="+e.getMessage());
            return null;
        }
    }

    /**
     * Get api to decrypt the encrypted string
     * @param encryptedString
     * @return decrypted string in plain text
     */
    @GET
    @Path("/decrypt/{encrypted}")
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String decrypt(@PathParam("encrypted") String encryptedString) {
        try {
            LOGGER.info("Request encryptedString==="+encryptedString);
            LOGGER.info("Calling decrypt service");
            return cryptoService.decrypt(encryptedString);
        } catch (Exception e) {
            LOGGER.error("Exception in decrypt==="+e.getMessage());
            return null;
        }
    }
}
