package br.com.dv.account.controller;

import br.com.dv.account.dto.securityevent.SecurityEventResponse;
import br.com.dv.account.service.securityevent.SecurityEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    public SecurityEventController(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @GetMapping("/events/")
    public ResponseEntity<List<SecurityEventResponse>> getSecurityEvents() {
        List<SecurityEventResponse> response = securityEventService.getSecurityEvents();
        return ResponseEntity.ok(response);
    }

}
