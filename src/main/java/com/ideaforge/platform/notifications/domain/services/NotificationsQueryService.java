package com.ideaforge.platform.notifications.domain.services;

import com.ideaforge.platform.notifications.domain.model.aggregates.Notification;
import com.ideaforge.platform.notifications.domain.model.queries.GetNotificationsByRecipientProfileIdQuery;

import java.util.List;

public interface NotificationsQueryService { List<Notification> handle(GetNotificationsByRecipientProfileIdQuery query); }
