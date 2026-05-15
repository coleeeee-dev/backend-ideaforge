package com.ideaforge.platform.iam.interfaces.rest.transform;

import com.ideaforge.platform.iam.domain.model.commands.LoginCommand;
import com.ideaforge.platform.iam.interfaces.rest.resources.LoginResource;

public class LoginCommandFromResourceAssembler { public static LoginCommand toCommandFromResource(LoginResource resource) { return new LoginCommand(resource.email(), resource.password()); } }
