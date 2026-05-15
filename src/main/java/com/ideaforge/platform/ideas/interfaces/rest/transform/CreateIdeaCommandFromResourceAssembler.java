package com.ideaforge.platform.ideas.interfaces.rest.transform;

import com.ideaforge.platform.ideas.domain.model.commands.CreateIdeaCommand;
import com.ideaforge.platform.ideas.domain.model.commands.CreateRequiredRoleCommand;
import com.ideaforge.platform.ideas.interfaces.rest.resources.CreateIdeaResource;

public class CreateIdeaCommandFromResourceAssembler {
    public static CreateIdeaCommand toCommandFromResource(CreateIdeaResource r) {
        var roles = r.requiredRoles() == null ? null : r.requiredRoles().stream().map(x -> new CreateRequiredRoleCommand(x.roleName(), x.description(), x.quantity(), x.requiredExperienceLevel())).toList();
        return new CreateIdeaCommand(r.creatorProfileId(), r.title(), r.shortDescription(), r.description(), r.problem(), r.solution(), r.category(), r.stage(), r.collaborationMode(), r.expectedCommitment(), roles);
    }
}
