package com.ideaforge.platform.collaboration.domain.model.aggregates;

import com.ideaforge.platform.collaboration.domain.model.entities.TeamMember;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.MemberStatus;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.TeamStatus;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Team extends AuditableAbstractAggregateRoot<Team> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long ideaId;

    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TeamStatus status = TeamStatus.FORMING;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> members = new ArrayList<>();

    public Team(Long ideaId, String name) {
        this.ideaId = ideaId;
        this.name = name;
    }

    public void addMember(Long profileId, String roleName) {
        if (members.stream().noneMatch(m -> m.getProfileId().equals(profileId))) {
            members.add(new TeamMember(this, profileId, roleName));
        }
        if (!members.isEmpty()) this.status = TeamStatus.ACTIVE;
    }

    public boolean removeMember(Long memberId) {
        return members.stream()
                .filter(member -> member.getId().equals(memberId))
                .findFirst()
                .map(member -> {
                    member.remove();
                    return true;
                })
                .orElse(false);
    }

    public boolean hasActiveMember(Long profileId) {
        return members.stream().anyMatch(member -> member.getProfileId().equals(profileId) && member.getMemberStatus() == MemberStatus.ACTIVE);
    }
}
