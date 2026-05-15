package com.ideaforge.platform.profiles.interfaces.rest.transform;

import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import com.ideaforge.platform.profiles.interfaces.rest.resources.*;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile e) {
        var skills = e.getSkills().stream().map(s -> new SkillResource(s.getId(), s.getName(), s.getProficiencyLevel().name())).toList();
        var interests = e.getInterests().stream().map(i -> new InterestResource(i.getId(), i.getName())).toList();
        return new ProfileResource(e.getId(), e.getAccountId(), e.getFirstName(), e.getLastName(), e.getHeadline(), e.getBio(), e.getAvatarUrl(), e.getExperienceLevel().name(), skills, interests);
    }
}
