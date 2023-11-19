package br.com.dv.account.repository;

import br.com.dv.account.entity.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {

    List<SecurityEvent> findAllByOrderByIdAsc();

}
