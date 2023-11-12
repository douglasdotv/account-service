package br.com.dv.account.controller;

import br.com.dv.account.dto.PaymentResponse;
import br.com.dv.account.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/payment")
    public ResponseEntity<List<PaymentResponse>> getPaymentOrPayments(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @RequestParam(required = false) String period) {
        List<PaymentResponse> response = employeeService.getPaymentOrPayments(userDetails.getUsername(), period);
        return ResponseEntity.ok(response);
    }

}
