package com.ideaforge.platform.ideas.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.domain.model.commands.AddRequiredRoleCommand;
import com.ideaforge.platform.ideas.domain.model.commands.CreateRequiredRoleCommand;
import com.ideaforge.platform.ideas.domain.model.valueobjects.RequiredExperienceLevel;
import com.ideaforge.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RequiredRole extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @Column(nullable = false, length = 100)
    private String roleName;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RequiredExperienceLevel requiredExperienceLevel = RequiredExperienceLevel.BEGINNER;

    public RequiredRole(Idea idea, CreateRequiredRoleCommand command) {
        this.idea = idea;
        this.roleName = command.roleName();
        this.description = command.description();
        this.quantity = command.quantity() == null ? 1 : command.quantity();
        this.requiredExperienceLevel = parse(command.requiredExperienceLevel());
    }

    public RequiredRole(Idea idea, AddRequiredRoleCommand command) {
        this.idea = idea;
        this.roleName = command.roleName();
        this.description = command.description();
        this.quantity = command.quantity() == null ? 1 : command.quantity();
        this.requiredExperienceLevel = parse(command.requiredExperienceLevel());
    }

    private RequiredExperienceLevel parse(String value) {
        if (value == null || value.isBlank()) return RequiredExperienceLevel.BEGINNER;
        return RequiredExperienceLevel.valueOf(value.trim().toUpperCase());
    }
}
