package pl.jach.gdanskinternshipdynatrace.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.jach.gdanskinternshipdynatrace.exception.BadDateFormatException;
import pl.jach.gdanskinternshipdynatrace.exception.NotFoundException;
import pl.jach.gdanskinternshipdynatrace.exception.NumberOutOfRangeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing tests for Exchange service
 */
@SpringBootTest(classes = RestTemplate.class)
class ExchangeServiceTest {

    private ExchangeService exchangeService;
    @BeforeEach
    public void setUp() {
        exchangeService = new ExchangeService();
    }
    @Test
    void givenCurrencyCodeAndDate_whenGetExchangeRateForSpecificDate_thenReturnAverageExchangeRate() {
        double expectedAverageExchangeRate = 5.2911;
        double actualAverageExchangeRate = (double) exchangeService.getExchangeRateForSpecificDate("GBP", "2023-01-03");
        assertEquals(expectedAverageExchangeRate, actualAverageExchangeRate);
    }

    @Test
    void givenBadCurrencyCode_whenGetExchangeRateForSpecificDate_thenThrowNotFoundException() {
        assertThrows(NotFoundException.class,() -> exchangeService.getExchangeRateForSpecificDate("GBBP", "2023-01-03"));
    }

    @Test
    void givenBadDateFormat_whenGetExchangeRateForSpecificDate_thenThrowBadDateException() {
        assertThrows(BadDateFormatException.class,() -> exchangeService.getExchangeRateForSpecificDate("GBP", "20023-01-03"));
    }


    @Test
    void givenCurrencyCodeAndNumberOfQuotations_whenGetMaxAndMinOfAvgExchangeRate_thenReturnMaxAndMinAverageExchangeRates() {
        String expectedMaxAndMinAverageExchangeRates ="Max: 5.3041\n" + "Min: 5.1958";
        String actualMaxAndMinAverageExchangeRates = (String) exchangeService.getMaxAndMinOfAvgExchangeRate("GBP", 10);
        assertEquals(expectedMaxAndMinAverageExchangeRates, actualMaxAndMinAverageExchangeRates);
    }

    @Test
    void givenBadCurrencyCode_whenGetMaxAndMinOfAvgExchangeRate_thenThrowNotFoundException() {
        assertThrows(HttpClientErrorException.NotFound.class,() -> exchangeService.getMaxAndMinOfAvgExchangeRate("GBBP", 3));
    }

    @Test
    void givenNumberOutOfRange_whenGetMaxAndMinOfAvgExchangeRate_thenNumberOutOfRangeException() {
        assertThrows(NumberOutOfRangeException.class,() -> exchangeService.getMaxAndMinOfAvgExchangeRate("GBP", 0));
    }

    @Test
    void givenCurrencyCodeAndNumberOfQuotations_whenGetMajorBuyAskDifference_thenReturnMajorDifference() {
        double expectedMajorDifference = 0.1095;
        double actualMajorDifference = (double) exchangeService.getMajorBuyAskDifference("GBP", 100);
        assertEquals(expectedMajorDifference, actualMajorDifference);
    }

    @Test
    void givenBadCurrencyCode_whenGetMajorBuyAskDifference_thenThrowNotFoundException() {
        assertThrows(HttpClientErrorException.NotFound.class,() -> exchangeService.getMajorBuyAskDifference("GBBP", 3));
    }

    @Test
    void givenNumberOutOfRange_henGetMajorBuyAskDifference_thenNumberOutOfRangeException() {
        assertThrows(NumberOutOfRangeException.class,() -> exchangeService.getMajorBuyAskDifference("GBP", 256));
    }
}