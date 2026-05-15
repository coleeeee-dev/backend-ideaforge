package com.ideaforge.platform.collaboration.domain.model.aggregates;

import com.ideaforge.platform.collaboration.domain.model.commands.CreateConversationCommand;
import com.ideaforge.platform.collaboration.domain.model.entities.Message;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Conversation extends AuditableAbstractAggregateRoot<Conversation> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ideaId;

    private Long projectApplicationId;

    @Column(nullable = false)
    private Long creatorProfileId;

    @Column(nullable = false)
    private Long applicantProfileId;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public Conversation(CreateConversationCommand command) {
        this.ideaId = command.ideaId();
        this.projectApplicationId = command.projectApplicationId();
        this.creatorProfileId = command.creatorProfileId();
        this.applicantProfileId = command.applicantProfileId();
    }

    public void addMessage(Long senderProfileId, String content) {
        this.messages.add(new Message(this, senderProfileId, content));
    }
}
