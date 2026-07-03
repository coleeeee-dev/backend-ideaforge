package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.valueobjects.TeamContact;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.TeamContactResource;

public class TeamContactResourceFromValueObjectAssembler {
    public static TeamContactResource toResourceFromValueObject(TeamContact contact) {
        return new TeamContactResource(contact.profileId(), contact.firstName(), contact.lastName(), contact.avatarUrl(), contact.roleName(), contact.phoneNumber(), contact.contactAvailable());
    }
}
