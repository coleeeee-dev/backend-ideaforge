package com.ideaforge.platform.iam.interfaces.rest.transform;

import com.ideaforge.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.ideaforge.platform.iam.interfaces.rest.resources.ChangePasswordResource;

public class ChangePasswordCommandFromResourceAssembler { public static ChangePasswordCommand toCommandFromResource(Long id, ChangePasswordResource resource) { return new ChangePasswordCommand(id, resource.currentPassword(), resource.newPassword()); } }
