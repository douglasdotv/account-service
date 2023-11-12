package br.com.dv.account.repository;

import br.com.dv.account.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByEmployeeEmailIgnoreCaseAndPeriod(String employeeEmail, String period);

    @Query("""
            SELECT e FROM Payment e
            WHERE LOWER(e.employeeEmail) = LOWER(:employeeEmail)
            ORDER BY e.periodYear DESC, e.periodMonth DESC
            """)
    List<Payment> findAllByEmployeeEmailOrderByPeriodDescIgnoreCase(@Param("employeeEmail") String employeeEmail);

    boolean existsByEmployeeEmailAndPeriod(String employeeEmail, String period);

}
