package com.ideaforge.platform.profiles.interfaces.rest.transform;

import com.ideaforge.platform.profiles.domain.model.commands.UpdateProfileSkillsCommand;
import com.ideaforge.platform.profiles.interfaces.rest.resources.UpdateProfileSkillsResource;

public class UpdateProfileSkillsCommandFromResourceAssembler { public static UpdateProfileSkillsCommand toCommandFromResource(Long id, UpdateProfileSkillsResource r) { return new UpdateProfileSkillsCommand(id, r.skills()); } }
