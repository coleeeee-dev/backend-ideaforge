package com.ideaforge.platform.notifications.interfaces.rest;

import com.ideaforge.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.ideaforge.platform.notifications.domain.model.queries.GetNotificationsByRecipientProfileIdQuery;
import com.ideaforge.platform.notifications.domain.services.NotificationsCommandService;
import com.ideaforge.platform.notifications.domain.services.NotificationsQueryService;
import com.ideaforge.platform.notifications.interfaces.rest.resources.CreateNotificationResource;
import com.ideaforge.platform.notifications.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import com.ideaforge.platform.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Notification endpoints")
public class NotificationsController {
    private final NotificationsCommandService commandService;
    private final NotificationsQueryService queryService;
    public NotificationsController(NotificationsCommandService commandService, NotificationsQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateNotificationResource resource) { var n = commandService.handle(CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(NotificationResourceFromEntityAssembler.toResourceFromEntity(n)); }
    @GetMapping("/by-recipient/{recipientProfileId}")
    public ResponseEntity<?> getByRecipient(@PathVariable Long recipientProfileId) { return ResponseEntity.ok(queryService.handle(new GetNotificationsByRecipientProfileIdQuery(recipientProfileId)).stream().map(NotificationResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @PostMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) { return ResponseEntity.ok(NotificationResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(new MarkNotificationAsReadCommand(notificationId)).orElseThrow())); }
}
