package com.ideaforge.platform.notifications.domain.model.aggregates;

import com.ideaforge.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.ideaforge.platform.notifications.domain.model.valueobjects.NotificationType;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class Notification extends AuditableAbstractAggregateRoot<Notification> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long recipientProfileId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private NotificationType type;
    @Column(nullable = false, length = 120)
    private String title;
    @Column(nullable = false, length = 255)
    private String body;
    private Instant readAt;
    public Notification(CreateNotificationCommand command) { this.recipientProfileId = command.recipientProfileId(); this.type = NotificationType.valueOf(command.type().trim().toUpperCase()); this.title = command.title(); this.body = command.body(); }
    public void markAsRead() { this.readAt = Instant.now(); }
}
