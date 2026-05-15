package com.ideaforge.platform.exploration.interfaces.rest;

import com.ideaforge.platform.exploration.domain.model.commands.UnsaveIdeaCommand;
import com.ideaforge.platform.exploration.domain.model.queries.GetSavedIdeasByProfileIdQuery;
import com.ideaforge.platform.exploration.domain.services.ExplorationQueryService;
import com.ideaforge.platform.exploration.domain.services.SavedIdeasCommandService;
import com.ideaforge.platform.exploration.interfaces.rest.resources.SaveIdeaResource;
import com.ideaforge.platform.exploration.interfaces.rest.transform.SaveIdeaCommandFromResourceAssembler;
import com.ideaforge.platform.exploration.interfaces.rest.transform.SavedIdeaResourceFromEntityAssembler;
import com.ideaforge.platform.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saved-ideas")
@Tag(name = "Saved Ideas", description = "Saved ideas endpoints")
public class SavedIdeasController {
    private final SavedIdeasCommandService commandService;
    private final ExplorationQueryService queryService;
    public SavedIdeasController(SavedIdeasCommandService commandService, ExplorationQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }
    @PostMapping
    public ResponseEntity<?> saveIdea(@Valid @RequestBody SaveIdeaResource resource) { var saved = commandService.handle(SaveIdeaCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(SavedIdeaResourceFromEntityAssembler.toResourceFromEntity(saved)); }
    @GetMapping("/by-profile/{profileId}")
    public ResponseEntity<?> getSavedIdeas(@PathVariable Long profileId) { return ResponseEntity.ok(queryService.handle(new GetSavedIdeasByProfileIdQuery(profileId)).stream().map(SavedIdeaResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @DeleteMapping
    public ResponseEntity<?> unsaveIdea(@RequestParam Long profileId, @RequestParam Long ideaId) { commandService.handle(new UnsaveIdeaCommand(profileId, ideaId)); return ResponseEntity.ok(MessageResource.of("Idea removed from saved list")); }
}
