package com.ideaforge.platform.collaboration.interfaces.rest;

import com.ideaforge.platform.collaboration.domain.model.queries.*;
import com.ideaforge.platform.collaboration.domain.services.ConversationsCommandService;
import com.ideaforge.platform.collaboration.domain.services.ConversationsQueryService;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.*;
import com.ideaforge.platform.collaboration.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/conversations")
@Tag(name = "Conversations", description = "Conversation and message endpoints")
public class ConversationsController {
    private final ConversationsCommandService commandService;
    private final ConversationsQueryService queryService;
    public ConversationsController(ConversationsCommandService commandService, ConversationsQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateConversationResource resource) { var conversation = commandService.handle(CreateConversationCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(ConversationResourceFromEntityAssembler.toResourceFromEntity(conversation)); }
    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getById(@PathVariable Long conversationId) {
        var conversation = queryService.handle(new GetConversationByIdQuery(conversationId));
        if (conversation.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ConversationResourceFromEntityAssembler.toResourceFromEntity(conversation.get()));
    }
    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long conversationId) {
        var conversation = queryService.handle(new GetConversationByIdQuery(conversationId));
        if (conversation.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ConversationResourceFromEntityAssembler.toResourceFromEntity(conversation.get()).messages());
    }
    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<?> sendMessage(@PathVariable Long conversationId, @Valid @RequestBody SendMessageResource resource) { var conversation = commandService.handle(SendMessageCommandFromResourceAssembler.toCommandFromResource(conversationId, resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(ConversationResourceFromEntityAssembler.toResourceFromEntity(conversation)); }
}
