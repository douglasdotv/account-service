package br.com.dv.account.validation;

import org.springframework.stereotype.Service;

@Service
public class EmployeeValidationService {

    private final CommonValidationService commonValidationService;

    public EmployeeValidationService(CommonValidationService commonValidationService) {
        this.commonValidationService = commonValidationService;
    }

    public void validatePeriod(String period) {
        commonValidationService.validatePeriod(period);
    }

}
