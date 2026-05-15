package com.ideaforge.platform.ideas.domain.model.aggregates;

import com.ideaforge.platform.ideas.domain.model.commands.*;
import com.ideaforge.platform.ideas.domain.model.entities.RequiredRole;
import com.ideaforge.platform.ideas.domain.model.valueobjects.*;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Idea extends AuditableAbstractAggregateRoot<Idea> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long creatorProfileId;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 255)
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String problem;

    @Column(columnDefinition = "TEXT")
    private String solution;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ProjectCategory category = ProjectCategory.OTHER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IdeaStatus status = IdeaStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IdeaStage stage = IdeaStage.IDEA;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CollaborationMode collaborationMode = CollaborationMode.REMOTE;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ExpectedCommitment expectedCommitment = ExpectedCommitment.MEDIUM;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequiredRole> requiredRoles = new ArrayList<>();

    public Idea(CreateIdeaCommand command) {
        this.creatorProfileId = command.creatorProfileId();
        this.title = command.title();
        this.shortDescription = command.shortDescription();
        this.description = command.description();
        this.problem = command.problem();
        this.solution = command.solution();
        this.category = parseCategory(command.category());
        this.stage = parseStage(command.stage());
        this.collaborationMode = parseMode(command.collaborationMode());
        this.expectedCommitment = parseCommitment(command.expectedCommitment());
        this.status = IdeaStatus.OPEN;
        if (command.requiredRoles() != null) {
            command.requiredRoles().forEach(this::addRequiredRoleFromCreateCommand);
        }
    }

    public void update(UpdateIdeaCommand command) {
        this.title = command.title();
        this.shortDescription = command.shortDescription();
        this.description = command.description();
        this.problem = command.problem();
        this.solution = command.solution();
        this.category = parseCategory(command.category());
        this.collaborationMode = parseMode(command.collaborationMode());
        this.expectedCommitment = parseCommitment(command.expectedCommitment());
    }

    public void updateStatus(String status) { this.status = IdeaStatus.valueOf(status.trim().toUpperCase()); }
    public void updateStage(String stage) { this.stage = parseStage(stage); }

    public void addRequiredRole(AddRequiredRoleCommand command) {
        this.requiredRoles.add(new RequiredRole(this, command));
    }

    private void addRequiredRoleFromCreateCommand(CreateRequiredRoleCommand command) {
        this.requiredRoles.add(new RequiredRole(this, command));
    }

    public void deactivate() { this.status = IdeaStatus.INACTIVE; }

    private ProjectCategory parseCategory(String value) { if (value == null || value.isBlank()) return ProjectCategory.OTHER; return ProjectCategory.valueOf(value.trim().toUpperCase()); }
    private IdeaStage parseStage(String value) { if (value == null || value.isBlank()) return IdeaStage.IDEA; return IdeaStage.valueOf(value.trim().toUpperCase()); }
    private CollaborationMode parseMode(String value) { if (value == null || value.isBlank()) return CollaborationMode.REMOTE; return CollaborationMode.valueOf(value.trim().toUpperCase()); }
    private ExpectedCommitment parseCommitment(String value) { if (value == null || value.isBlank()) return ExpectedCommitment.MEDIUM; return ExpectedCommitment.valueOf(value.trim().toUpperCase()); }
}
