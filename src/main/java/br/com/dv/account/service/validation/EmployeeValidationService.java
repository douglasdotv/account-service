package br.com.dv.account.service.validation;

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
