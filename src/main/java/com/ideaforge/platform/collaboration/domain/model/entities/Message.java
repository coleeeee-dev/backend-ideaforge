package com.ideaforge.platform.collaboration.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import com.ideaforge.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class Message extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Column(nullable = false)
    private Long senderProfileId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Instant sentAt = Instant.now();

    public Message(Conversation conversation, Long senderProfileId, String content) {
        if (content == null || content.isBlank()) throw new IllegalArgumentException("Message cannot be blank");
        this.conversation = conversation;
        this.senderProfileId = senderProfileId;
        this.content = content;
    }
}
