package com.webservices.crypto.service;

import com.google.inject.Inject;
import com.webservices.crypto.config.CryptoConfiguration;
import com.webservices.crypto.domain.RunningStatistics;
import com.webservices.crypto.model.Statistics;
import com.webservices.crypto.util.EncryptDecryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Crypto Service Implementation class
 */
public class CryptoServiceImpl implements CryptoService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoServiceImpl.class);

    @Inject
    CryptoConfiguration configuration;

    /**
     * PushAndRecalculate method to push
     * the number to running stream and calculate stats
     * @param number
     * @return Statistics Object with running mean
     * and standard deviation
     */
    @Override
    public Statistics pushAndRecalculate(Double number){
        LOGGER.info(MessageFormat.format("Pushing number== {0} to statsCalculation", number));
        //Push it to running stream
        RunningStatistics.getInstance().push(number);

        // Read the mean and std
        double mean = RunningStatistics.getInstance().getMean();
        double stdDev = RunningStatistics.getInstance().getStdDev();

        LOGGER.info("Mean=="+mean+","+"Standard Deviation=="+stdDev);
        Statistics stats = new Statistics(String.valueOf(mean),String.valueOf(stdDev));
        return stats;
    }

    /**
     * PushRecalculateAndEncrypt method to push
     * the number to running stream and calculate stats
     * @param number
     * @return
     * @throws Exception
     */
    @Override
    public Statistics pushRecalculateAndEncrypt(Double number) throws Exception{
        LOGGER.info(MessageFormat.format("Pushing number== {0} to statsCalculation", number));

        //Push it to running stream
        RunningStatistics.getInstance().push(number);

        // Read the mean and std
        double mean = RunningStatistics.getInstance().getMean();
        double stdDev = RunningStatistics.getInstance().getStdDev();

        LOGGER.info("Before Encryption");

        LOGGER.info("Mean=="+mean+","+"Standard Deviation=="+stdDev);

        // Get the Encrypted String
        String mean_str =
                EncryptDecryptUtil.encrypt(String.valueOf(mean),configuration.getSecretKey());
        String std_str =
                EncryptDecryptUtil.encrypt(String.valueOf(stdDev),configuration.getSecretKey());

        LOGGER.info("After Encryption");

        LOGGER.info("Mean=="+mean_str+","+"Standard Deviation=="+std_str);

        Statistics stats = new Statistics(mean_str,std_str);
        return stats;
    }

    /**
     * Decrypt method to decrypt the
     * encrypted string
     * @param encryptedString
     * @return decrypted string
     * @throws Exception
     */
    @Override
    public String decrypt(String encryptedString) throws Exception{
        LOGGER.info(MessageFormat.format("Call decrypt service to decrypt string== {0}", encryptedString));

        //call the decrypt util
        return EncryptDecryptUtil.decrypt(encryptedString, configuration.getSecretKey());
    }
}
