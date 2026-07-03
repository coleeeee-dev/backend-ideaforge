package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.valueobjects.MemberStatus;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.*;

public class TeamResourceFromEntityAssembler {
    public static TeamResource toResourceFromEntity(Team e) {
        var members = e.getMembers().stream().filter(m -> m.getMemberStatus() == MemberStatus.ACTIVE).map(m -> new TeamMemberResource(m.getId(), m.getProfileId(), m.getRoleName(), m.getMemberStatus().name())).toList();
        return new TeamResource(e.getId(), e.getIdeaId(), e.getName(), e.getStatus().name(), members);
    }
}
