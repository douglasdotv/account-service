package br.com.dv.account.service;

import br.com.dv.account.dto.PaymentResponse;
import br.com.dv.account.entity.User;
import br.com.dv.account.exception.custom.UserAuthenticationMismatchException;
import br.com.dv.account.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;

    public EmployeeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PaymentResponse getPayment(String userEmail) {
        User user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UserAuthenticationMismatchException(userEmail));

        return new PaymentResponse(user.getId(), user.getName(), user.getLastName(), user.getEmail());
    }

}
