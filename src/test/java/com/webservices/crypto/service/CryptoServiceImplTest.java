package com.webservices.crypto.service;

import com.webservices.crypto.config.CryptoConfiguration;
import com.webservices.crypto.model.Statistics;
import com.webservices.crypto.util.EncryptDecryptUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CryptoServiceImplTest {
    private static CryptoService cryptoService;

    @Mock
    private CryptoConfiguration config;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setupData() {
        cryptoService = new CryptoServiceImpl();
    }

    @Test
    public void testGetStats() throws Exception {
        Statistics statistics = null;
        statistics = cryptoService.pushAndRecalculate(4.0);

        assertEquals(String.valueOf(4.0), statistics.getMean());
        assertEquals(String.valueOf(0.0), statistics.getStandardDeviation());

        statistics = cryptoService.pushAndRecalculate(7.0);
        assertEquals(String.valueOf(5.5), statistics.getMean());
        assertEquals(String.valueOf(1.5), statistics.getStandardDeviation());
        assertNotEquals(String.valueOf(0.0),statistics.getMean());
        assertNotEquals(String.valueOf(Double.NaN),statistics.getStandardDeviation());

        statistics = cryptoService.pushAndRecalculate(6.0);
        assertEquals(String.valueOf(5.666666666666667), statistics.getMean());
        assertEquals(String.valueOf(1.247219128924647), statistics.getStandardDeviation());

        /** TODO Test */
        //when(config.getSecretKey()).thenReturn("abcdefghijklmnopqrstuvwxyz");
        //statistics = cryptoService.pushRecalculateAndEncrypt(9.0);
        //assertEquals(String.valueOf(6.5), cryptoService.decrypt(statistics.getMean()));

    }
}