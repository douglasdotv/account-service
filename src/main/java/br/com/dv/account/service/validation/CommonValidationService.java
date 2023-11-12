package br.com.dv.account.service.validation;

import br.com.dv.account.exception.custom.InvalidPeriodException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CommonValidationService {

    private static final String EXPECTED_PERIOD_FORMAT = "MM-yyyy";

    public void validatePeriod(String period) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EXPECTED_PERIOD_FORMAT);
            YearMonth.parse(period, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPeriodException();
        }
    }

}
