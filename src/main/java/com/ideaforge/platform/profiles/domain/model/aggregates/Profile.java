package com.ideaforge.platform.profiles.domain.model.aggregates;

import com.ideaforge.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.ideaforge.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.ideaforge.platform.profiles.domain.model.entities.Interest;
import com.ideaforge.platform.profiles.domain.model.entities.Skill;
import com.ideaforge.platform.profiles.domain.model.valueobjects.ExperienceLevel;
import com.ideaforge.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long accountId;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(length = 120)
    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 255)
    private String avatarUrl;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean sharePhoneWithTeam = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ExperienceLevel experienceLevel = ExperienceLevel.BEGINNER;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    public Profile(CreateProfileCommand command) {
        this.accountId = command.accountId();
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.headline = command.headline();
        this.bio = command.bio();
        this.avatarUrl = command.avatarUrl();
        this.experienceLevel = parseExperience(command.experienceLevel());
        updateSkills(command.skills());
        updateInterests(command.interests());
    }

    public void update(UpdateProfileCommand command) {
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.headline = command.headline();
        this.bio = command.bio();
        this.avatarUrl = command.avatarUrl();
        this.experienceLevel = parseExperience(command.experienceLevel());
    }

    public void updateSkills(List<String> values) {
        this.skills.clear();
        if (values != null) {
            values.stream()
                    .filter(v -> v != null && !v.isBlank())
                    .map(String::trim)
                    .distinct()
                    .map(value -> new Skill(this, value))
                    .forEach(this.skills::add);
        }
    }

    public void updateInterests(List<String> values) {
        this.interests.clear();
        if (values != null) {
            values.stream()
                    .filter(v -> v != null && !v.isBlank())
                    .map(String::trim)
                    .distinct()
                    .map(value -> new Interest(this, value))
                    .forEach(this.interests::add);
        }
    }

    public void updateContactSettings(String phoneNumber, boolean sharePhoneWithTeam) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            this.phoneNumber = null;
            this.sharePhoneWithTeam = false;
            return;
        }

        var normalizedPhoneNumber = phoneNumber.trim();
        if (normalizedPhoneNumber.length() > 20 || !normalizedPhoneNumber.matches("^\\+[1-9]\\d{1,14}$")) {
            throw new IllegalArgumentException("Phone number must be in E.164 format");
        }

        this.phoneNumber = normalizedPhoneNumber;
        this.sharePhoneWithTeam = sharePhoneWithTeam;
    }

    private ExperienceLevel parseExperience(String value) {
        if (value == null || value.isBlank()) return ExperienceLevel.BEGINNER;
        return ExperienceLevel.valueOf(value.trim().toUpperCase());
    }
}
