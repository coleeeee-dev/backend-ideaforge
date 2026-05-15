package com.ideaforge.platform.collaboration.domain.model.aggregates;

import com.ideaforge.platform.collaboration.domain.model.commands.ApplyToIdeaCommand;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.ApplicationStatus;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class ProjectApplication extends AuditableAbstractAggregateRoot<ProjectApplication> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long ideaId;
    @Column(nullable = false)
    private Long applicantProfileId;
    @Column(length = 100)
    private String requestedRole;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    private Instant decidedAt;

    public ProjectApplication(ApplyToIdeaCommand command) { this.ideaId = command.ideaId(); this.applicantProfileId = command.applicantProfileId(); this.requestedRole = command.requestedRole(); this.message = command.message(); }
    public void accept() { this.status = ApplicationStatus.ACCEPTED; this.decidedAt = Instant.now(); }
    public void reject() { this.status = ApplicationStatus.REJECTED; this.decidedAt = Instant.now(); }
    public boolean isPending() { return status == ApplicationStatus.PENDING; }
}
