package com.ideaforge.platform.profiles.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Interest extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false, length = 80)
    private String name;

    public Interest(Profile profile, String name) {
        this.profile = profile;
        this.name = name.trim();
    }
}
