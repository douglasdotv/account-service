package br.com.dv.account.service.securityevent;

import br.com.dv.account.dto.securityevent.SecurityEventResponse;

import java.util.List;

public interface SecurityEventService {

    List<SecurityEventResponse> getSecurityEvents();

}
