package com.ideaforge.platform.notifications.application.internal.commandservices;

import com.ideaforge.platform.notifications.domain.exceptions.NotificationNotFoundException;
import com.ideaforge.platform.notifications.domain.model.aggregates.Notification;
import com.ideaforge.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.ideaforge.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.ideaforge.platform.notifications.domain.services.NotificationsCommandService;
import com.ideaforge.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationsCommandServiceImpl implements NotificationsCommandService {
    private final NotificationRepository repository;
    public NotificationsCommandServiceImpl(NotificationRepository repository) { this.repository = repository; }
    public Optional<Notification> handle(CreateNotificationCommand command) { return Optional.of(repository.save(new Notification(command))); }
    public Optional<Notification> handle(MarkNotificationAsReadCommand command) { var notification = repository.findById(command.notificationId()).orElseThrow(() -> new NotificationNotFoundException(command.notificationId())); notification.markAsRead(); return Optional.of(repository.save(notification)); }
}
