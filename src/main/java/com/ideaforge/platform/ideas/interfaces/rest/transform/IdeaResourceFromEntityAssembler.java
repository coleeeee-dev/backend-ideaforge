package com.ideaforge.platform.ideas.interfaces.rest.transform;

import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.interfaces.rest.resources.*;

public class IdeaResourceFromEntityAssembler {
    public static IdeaResource toResourceFromEntity(Idea e) {
        var roles = e.getRequiredRoles().stream().map(r -> new RequiredRoleResource(r.getId(), r.getRoleName(), r.getDescription(), r.getQuantity(), r.getRequiredExperienceLevel().name())).toList();
        return new IdeaResource(e.getId(), e.getCreatorProfileId(), e.getTitle(), e.getShortDescription(), e.getDescription(), e.getProblem(), e.getSolution(), e.getCategory().name(), e.getStatus().name(), e.getStage().name(), e.getCollaborationMode().name(), e.getExpectedCommitment().name(), roles);
    }
}
