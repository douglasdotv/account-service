package br.com.dv.account.entity;

import br.com.dv.account.enums.SecurityAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "security_event")
public class SecurityEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_event_id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private SecurityAction action;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "object", nullable = false)
    private String object;

    @Column(name = "path", nullable = false)
    private String path;

    protected SecurityEvent() {
    }

    public SecurityEvent(SecurityAction action, String subject, String object, String path) {
        this.date = LocalDateTime.now();
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

}
