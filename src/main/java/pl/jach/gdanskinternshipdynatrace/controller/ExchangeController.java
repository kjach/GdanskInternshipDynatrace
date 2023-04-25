package pl.jach.gdanskinternshipdynatrace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import pl.jach.gdanskinternshipdynatrace.exception.BadDateFormatException;
import pl.jach.gdanskinternshipdynatrace.exception.NotFoundException;
import pl.jach.gdanskinternshipdynatrace.exception.NumberOutOfRangeException;
import pl.jach.gdanskinternshipdynatrace.service.ExchangeService;

@RestController
@RequestMapping("/exchangerates")
public class ExchangeController {

    private final ExchangeService exchangeService = new ExchangeService();

    /**
     * Route for getting the average exchange rate for a specific currency and date.
     * @param currencyCode A three-character string code for given currency, ex. GBP
     * @param date An int number of last quotations in range between 1 and 255
     * @return ResponseEntity with an average exchange rate
     * @throws BadDateFormatException with code 400 when date is in format different from YYYY-MM-DD
     * @throws NotFoundException with code 404 when the application cannot find the average exchange rate
     */
    @GetMapping("/{currencyCode}/{date}")
    public ResponseEntity<?> getExchangeRateForSpecificDate(@PathVariable("currencyCode") String currencyCode, @PathVariable("date") String date) throws BadDateFormatException, NotFoundException {
        try {
            return ResponseEntity.ok(exchangeService.getExchangeRateForSpecificDate(currencyCode, date));
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException();
        }
    }

    /**
     * Route for getting the maximum and minimum average exchange rates for a specific currency and
     * number of last quotations.
     * @param currencyCode A three-character string code for given currency, ex. GBP
     * @param numberOfQuotations An int number of last quotations in range between 1 and 255
     * @return ResponseEntity with found maximum and minimum average exchange rates
     * @throws NotFoundException with code 404 when the application cannot find the average exchange rate
     * @throws NumberOutOfRangeException with code 400 when the number is either less than 1 or more than 255
     */
    @GetMapping("/{currencyCode}/max-min-average/{numberOfQuotations}")
    public ResponseEntity<?> getMaxAndMinOfAvgExchangeRate(@PathVariable("currencyCode") String currencyCode, @PathVariable("numberOfQuotations") int numberOfQuotations) throws NotFoundException, NumberOutOfRangeException {
        try {
            return ResponseEntity.ok(exchangeService.getMaxAndMinOfAvgExchangeRate(currencyCode, numberOfQuotations));
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException();
        }
    }

    /**
     * Route for getting the major difference between the buy and ask rate
     * for a given currency and number of last quotations.
     * @param currencyCode A three-character string code for given currency, ex. GBP
     * @param numberOfQuotations An int number of last quotations in range between 1 and 255
     * @return ResponseEntity with major buy and ask difference
     * @throws NotFoundException with code 404 when the application cannot find the average exchange rate
     * @throws NumberOutOfRangeException with code 400 when the number is either less than 1 or more than 255
     */
    @GetMapping("/{currencyCode}/major-difference/{numberOfQuotations}")
    public ResponseEntity<?> getMajorBuyAskDifference(@PathVariable("currencyCode") String currencyCode, @PathVariable("numberOfQuotations") int numberOfQuotations) throws NotFoundException, NumberOutOfRangeException {
        try {
            return ResponseEntity.ok(exchangeService.getMajorBuyAskDifference(currencyCode, numberOfQuotations));
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException();
        }
    }
}
