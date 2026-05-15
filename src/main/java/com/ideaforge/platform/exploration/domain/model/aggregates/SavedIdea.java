package com.ideaforge.platform.exploration.domain.model.aggregates;

import com.ideaforge.platform.exploration.domain.model.commands.SaveIdeaCommand;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class SavedIdea extends AuditableAbstractAggregateRoot<SavedIdea> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long profileId;
    @Column(nullable = false)
    private Long ideaId;
    public SavedIdea(SaveIdeaCommand command) { this.profileId = command.profileId(); this.ideaId = command.ideaId(); }
}
