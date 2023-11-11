package br.com.dv.account.controller;

import br.com.dv.account.dto.PaymentResponse;
import br.com.dv.account.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/payment")
    public ResponseEntity<PaymentResponse> getPayment(@AuthenticationPrincipal UserDetails userDetails) {
        PaymentResponse response = employeeService.getPayment(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

}
