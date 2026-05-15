package com.ideaforge.platform.iam.interfaces.rest.transform;

import com.ideaforge.platform.iam.domain.model.commands.UpdateAccountCommand;
import com.ideaforge.platform.iam.domain.model.valueobjects.AccountStatus;
import com.ideaforge.platform.iam.interfaces.rest.resources.UpdateAccountResource;

public class UpdateAccountCommandFromResourceAssembler { public static UpdateAccountCommand toCommandFromResource(Long id, UpdateAccountResource resource) { return new UpdateAccountCommand(id, AccountStatus.valueOf(resource.status().toUpperCase())); } }
