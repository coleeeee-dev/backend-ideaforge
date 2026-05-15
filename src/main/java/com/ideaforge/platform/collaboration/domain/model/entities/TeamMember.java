package com.ideaforge.platform.collaboration.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.MemberStatus;
import com.ideaforge.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
public class TeamMember extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false, length = 100)
    private String roleName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    @Column(nullable = false)
    private Instant joinedAt = Instant.now();

    public TeamMember(Team team, Long profileId, String roleName) {
        this.team = team;
        this.profileId = profileId;
        this.roleName = roleName == null || roleName.isBlank() ? "Collaborator" : roleName;
    }
}
