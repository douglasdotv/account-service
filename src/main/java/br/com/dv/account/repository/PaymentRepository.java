package br.com.dv.account.repository;

import br.com.dv.account.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByEmployeeEmailIgnoreCaseAndPeriod(String employeeEmail, String period);

    boolean existsByEmployeeEmailAndPeriod(String employeeEmail, String period);

}
