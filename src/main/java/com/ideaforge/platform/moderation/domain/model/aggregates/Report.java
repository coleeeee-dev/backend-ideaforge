package com.ideaforge.platform.moderation.domain.model.aggregates;

import com.ideaforge.platform.moderation.domain.model.commands.CreateReportCommand;
import com.ideaforge.platform.moderation.domain.model.commands.ResolveReportCommand;
import com.ideaforge.platform.moderation.domain.model.valueobjects.*;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class Report extends AuditableAbstractAggregateRoot<Report> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long reporterProfileId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TargetType targetType;
    @Column(nullable = false)
    private Long targetId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ReportReason reason;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportStatus status = ReportStatus.PENDING;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ModerationDecision decision;
    private Long resolvedByAccountId;
    private Instant resolvedAt;

    public Report(CreateReportCommand command) {
        this.reporterProfileId = command.reporterProfileId();
        this.targetType = TargetType.valueOf(command.targetType().trim().toUpperCase());
        this.targetId = command.targetId();
        this.reason = ReportReason.valueOf(command.reason().trim().toUpperCase());
        this.description = command.description();
    }

    public void resolve(ResolveReportCommand command) {
        this.status = ReportStatus.valueOf(command.status().trim().toUpperCase());
        this.decision = ModerationDecision.valueOf(command.decision().trim().toUpperCase());
        this.resolvedByAccountId = command.resolvedByAccountId();
        this.resolvedAt = Instant.now();
    }
}
