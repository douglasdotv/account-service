package br.com.dv.account.controller;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.dto.PaymentUploadResponse;
import br.com.dv.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acct")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentUploadResponse> addPayments(@RequestBody List<@Valid PaymentUploadRequest> request) {
        PaymentUploadResponse response = accountService.addPayments(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/payments")
    public ResponseEntity<PaymentUploadResponse> updatePayment(@Valid @RequestBody PaymentUploadRequest request) {
        PaymentUploadResponse response = accountService.updatePayment(request);
        return ResponseEntity.ok(response);
    }

}
