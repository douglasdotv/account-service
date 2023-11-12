package br.com.dv.account.controller;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.dto.PaymentUploadResponse;
import br.com.dv.account.service.AccountingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acct")
public class AccountingController {

    private final AccountingService accountingService;

    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentUploadResponse> addPayments(@RequestBody List<@Valid PaymentUploadRequest> request) {
        PaymentUploadResponse response = accountingService.addPayments(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/payments")
    public ResponseEntity<PaymentUploadResponse> updatePayment(@Valid @RequestBody PaymentUploadRequest request) {
        PaymentUploadResponse response = accountingService.updatePayment(request);
        return ResponseEntity.ok(response);
    }

}
