package com.ideaforge.platform.notifications.application.internal.queryservices;

import com.ideaforge.platform.notifications.domain.model.aggregates.Notification;
import com.ideaforge.platform.notifications.domain.model.queries.GetNotificationsByRecipientProfileIdQuery;
import com.ideaforge.platform.notifications.domain.services.NotificationsQueryService;
import com.ideaforge.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsQueryServiceImpl implements NotificationsQueryService {
    private final NotificationRepository repository;
    public NotificationsQueryServiceImpl(NotificationRepository repository) { this.repository = repository; }
    public List<Notification> handle(GetNotificationsByRecipientProfileIdQuery query) { return repository.findByRecipientProfileId(query.recipientProfileId()); }
}
