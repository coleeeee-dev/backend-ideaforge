package com.ideaforge.platform.ideas.interfaces.rest.transform;

import com.ideaforge.platform.ideas.domain.model.commands.AddRequiredRoleCommand;
import com.ideaforge.platform.ideas.interfaces.rest.resources.CreateRequiredRoleResource;

public class AddRequiredRoleCommandFromResourceAssembler { public static AddRequiredRoleCommand toCommandFromResource(Long ideaId, CreateRequiredRoleResource r) { return new AddRequiredRoleCommand(ideaId, r.roleName(), r.description(), r.quantity(), r.requiredExperienceLevel()); } }
