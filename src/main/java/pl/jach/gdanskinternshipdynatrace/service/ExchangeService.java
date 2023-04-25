package pl.jach.gdanskinternshipdynatrace.service;

import pl.jach.gdanskinternshipdynatrace.exception.BadDateFormatException;
import pl.jach.gdanskinternshipdynatrace.exception.NotFoundException;
import pl.jach.gdanskinternshipdynatrace.exception.NumberOutOfRangeException;
import pl.jach.gdanskinternshipdynatrace.model.Exchange;
import pl.jach.gdanskinternshipdynatrace.model.Rate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Pattern;

@Service
public class ExchangeService {
    private static final String URL_TABLE_A = "http://api.nbp.pl/api/exchangerates/rates/a/";
    private static final String URL_TABLE_C = "http://api.nbp.pl/api/exchangerates/rates/c/";

    RestTemplate restTemplate = new RestTemplate();

    /**
     * Method used to find average exchange rate for given date and currency.
     * @param currencyCode A three-character string code for given currency, ex. GBP
     * @param date A String containing date in format YYYY-MM-DD
     * @return Returns average exchange rate for given currency and date
     */
    public Object getExchangeRateForSpecificDate(String currencyCode, String date) {
        try {
            if (!Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", date)) {
                throw new BadDateFormatException();
            }
            ResponseEntity<Exchange> response = restTemplate.getForEntity(URL_TABLE_A + currencyCode +"/" + date, Exchange.class);
            return response.getBody().getRates()[0].getMid();
        }catch (HttpClientErrorException ex) {
            throw new NotFoundException();
        }
    }

    /**
     * Method used to find the maximum and minimum of average exchange rate for a given currency
     * and number of last quotations.
     * @param currencyCode A three-character string code for given currency, ex. GBP
     * @param numberOfQuotations An int number of last quotations in range between 1 and 255
     * @return Returns the found maximum and minimum of average exchange rates
     */
    public Object getMaxAndMinOfAvgExchangeRate(String currencyCode, int numberOfQuotations){
        if (numberOfQuotations > 255 || numberOfQuotations < 1) {
            throw new NumberOutOfRangeException();
        }
        ResponseEntity<Exchange> response = restTemplate.getForEntity(URL_TABLE_A + currencyCode + "/last/"+ numberOfQuotations, Exchange.class);
        Rate[] rates = response.getBody().getRates();
        double min = rates[0].getMid();
        double max = 0;
        for (Rate rate : rates) {
            min = Math.min(min, rate.getMid());
            max = Math.max(max, rate.getMid());
        }
        return "Max: " + max + "\nMin: " + min;
    }

    /**
     * Method used to find the major difference between the buy and ask rate
     * fora given currency and number of last quotations
     * @param currencyCode A three-character string code for given currency, ex. GBP
     * @param numberOfQuotations An int number of last quotations in range between 1 and 255
     * @return Returns the found major difference of buy and ask rates
     */
    public Object getMajorBuyAskDifference(String currencyCode, int numberOfQuotations){
        if (numberOfQuotations > 255 || numberOfQuotations < 1) {
            throw new NumberOutOfRangeException();
        }
        ResponseEntity<Exchange> response = restTemplate.getForEntity(URL_TABLE_C + currencyCode + "/last/"+ numberOfQuotations, Exchange.class);
        Rate[] rates = response.getBody().getRates();
        double bid, ask, difference, maxDifference=0;
        for(Rate rate : rates) {
            bid = rate.getBid();
            ask = rate.getAsk();
            difference = ask-bid;
            if (difference > maxDifference) {
                maxDifference = difference;
            }
        }
        return maxDifference;
    }
}
