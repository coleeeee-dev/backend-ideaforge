package com.ideaforge.platform.notifications.domain.services;

import com.ideaforge.platform.notifications.domain.model.aggregates.Notification;
import com.ideaforge.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.ideaforge.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;

import java.util.Optional;

public interface NotificationsCommandService {
    Optional<Notification> handle(CreateNotificationCommand command);
    Optional<Notification> handle(MarkNotificationAsReadCommand command);
}
