package br.com.dv.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String employeeEmail;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private Long salary;

    @Column(name = "period_year", nullable = false)
    private int periodYear;

    @Column(name = "period_month", nullable = false)
    private int periodMonth;

    @PrePersist
    @PreUpdate
    private void updatePeriodFields() {
        String[] periodParts = this.period.split("-");
        this.periodMonth = Integer.parseInt(periodParts[0]);
        this.periodYear = Integer.parseInt(periodParts[1]);
    }

}
