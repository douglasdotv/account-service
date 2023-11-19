package br.com.dv.account.service.securityevent;

import br.com.dv.account.dto.securityevent.SecurityEventResponse;
import br.com.dv.account.entity.SecurityEvent;
import br.com.dv.account.mapper.SecurityEventMapper;
import br.com.dv.account.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SecurityEventServiceImpl implements SecurityEventService {

    private final SecurityEventRepository securityEventRepository;
    private final SecurityEventMapper securityEventMapper;

    public SecurityEventServiceImpl(SecurityEventRepository securityEventRepository,
                                    SecurityEventMapper securityEventMapper) {
        this.securityEventRepository = securityEventRepository;
        this.securityEventMapper = securityEventMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecurityEventResponse> getSecurityEvents() {
        List<SecurityEvent> securityEvents = securityEventRepository.findAllByOrderByIdAsc();
        return securityEventMapper.toSecurityEventResponseList(securityEvents);
    }

}
