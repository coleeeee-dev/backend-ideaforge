package com.ideaforge.platform.iam.interfaces.rest.transform;

import com.ideaforge.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.ideaforge.platform.iam.interfaces.rest.resources.RegisterAccountResource;

public class RegisterAccountCommandFromResourceAssembler { public static RegisterAccountCommand toCommandFromResource(RegisterAccountResource resource) { return new RegisterAccountCommand(resource.email(), resource.password()); } }
