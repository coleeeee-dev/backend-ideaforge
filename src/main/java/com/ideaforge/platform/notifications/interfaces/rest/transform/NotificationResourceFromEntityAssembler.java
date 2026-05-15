package com.ideaforge.platform.notifications.interfaces.rest.transform;

import com.ideaforge.platform.notifications.domain.model.aggregates.Notification;
import com.ideaforge.platform.notifications.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler { public static NotificationResource toResourceFromEntity(Notification e) { return new NotificationResource(e.getId(), e.getRecipientProfileId(), e.getType().name(), e.getTitle(), e.getBody(), e.getReadAt() == null ? null : e.getReadAt().toString()); } }
