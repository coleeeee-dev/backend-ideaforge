package com.ideaforge.platform.profiles.interfaces.rest.transform;

import com.ideaforge.platform.profiles.domain.model.commands.UpdateProfileInterestsCommand;
import com.ideaforge.platform.profiles.interfaces.rest.resources.UpdateProfileInterestsResource;

public class UpdateProfileInterestsCommandFromResourceAssembler { public static UpdateProfileInterestsCommand toCommandFromResource(Long id, UpdateProfileInterestsResource r) { return new UpdateProfileInterestsCommand(id, r.interests()); } }
