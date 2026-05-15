package com.ideaforge.platform.notifications.interfaces.rest.transform;

import com.ideaforge.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.ideaforge.platform.notifications.interfaces.rest.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler { public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource r) { return new CreateNotificationCommand(r.recipientProfileId(), r.type(), r.title(), r.body()); } }
