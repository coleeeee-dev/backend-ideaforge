package com.ideaforge.platform.ideas.interfaces.rest;

import com.ideaforge.platform.ideas.domain.model.commands.DeleteIdeaCommand;
import com.ideaforge.platform.ideas.domain.model.queries.*;
import com.ideaforge.platform.ideas.domain.services.IdeasCommandService;
import com.ideaforge.platform.ideas.domain.services.IdeasQueryService;
import com.ideaforge.platform.ideas.interfaces.rest.resources.*;
import com.ideaforge.platform.ideas.interfaces.rest.transform.*;
import com.ideaforge.platform.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ideas")
@Tag(name = "Ideas", description = "Idea management endpoints")
public class IdeasController {
    private final IdeasCommandService commandService;
    private final IdeasQueryService queryService;
    public IdeasController(IdeasCommandService commandService, IdeasQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }

    @PostMapping
    public ResponseEntity<?> createIdea(@Valid @RequestBody CreateIdeaResource resource) { var idea = commandService.handle(CreateIdeaCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(IdeaResourceFromEntityAssembler.toResourceFromEntity(idea)); }
    @GetMapping
    public ResponseEntity<?> getAllIdeas(@RequestParam(required = false) String status) { var ideas = status == null ? queryService.handle(new GetAllIdeasQuery()) : queryService.handle(new GetIdeasByStatusQuery(status)); return ResponseEntity.ok(ideas.stream().map(IdeaResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @GetMapping("/{ideaId}")
    public ResponseEntity<?> getIdeaById(@PathVariable Long ideaId) {
        var idea = queryService.handle(new GetIdeaByIdQuery(ideaId));
        if (idea.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(IdeaResourceFromEntityAssembler.toResourceFromEntity(idea.get()));
    }
    @GetMapping("/by-creator/{creatorProfileId}")
    public ResponseEntity<?> getIdeasByCreator(@PathVariable Long creatorProfileId) { return ResponseEntity.ok(queryService.handle(new GetIdeasByCreatorIdQuery(creatorProfileId)).stream().map(IdeaResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @PutMapping("/{ideaId}")
    public ResponseEntity<?> updateIdea(@PathVariable Long ideaId, @Valid @RequestBody UpdateIdeaResource resource) { return ResponseEntity.ok(IdeaResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(UpdateIdeaCommandFromResourceAssembler.toCommandFromResource(ideaId, resource)).orElseThrow())); }
    @DeleteMapping("/{ideaId}")
    public ResponseEntity<?> deleteIdea(@PathVariable Long ideaId) { commandService.handle(new DeleteIdeaCommand(ideaId)); return ResponseEntity.ok(MessageResource.of("Idea deactivated")); }
    @PutMapping("/{ideaId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long ideaId, @Valid @RequestBody UpdateIdeaStatusResource resource) { return ResponseEntity.ok(IdeaResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(UpdateIdeaStatusCommandFromResourceAssembler.toCommandFromResource(ideaId, resource)).orElseThrow())); }
    @PutMapping("/{ideaId}/stage")
    public ResponseEntity<?> updateStage(@PathVariable Long ideaId, @Valid @RequestBody UpdateIdeaStageResource resource) { return ResponseEntity.ok(IdeaResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(UpdateIdeaStageCommandFromResourceAssembler.toCommandFromResource(ideaId, resource)).orElseThrow())); }
    @PostMapping("/{ideaId}/required-roles")
    public ResponseEntity<?> addRequiredRole(@PathVariable Long ideaId, @Valid @RequestBody CreateRequiredRoleResource resource) { return ResponseEntity.status(HttpStatus.CREATED).body(IdeaResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(AddRequiredRoleCommandFromResourceAssembler.toCommandFromResource(ideaId, resource)).orElseThrow())); }
}
