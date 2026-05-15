package com.ideaforge.platform.collaboration.interfaces.rest;

import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.services.TeamsQueryService;
import com.ideaforge.platform.collaboration.interfaces.rest.transform.TeamResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@Tag(name = "Teams", description = "Team endpoints")
public class TeamsController {
    private final TeamsQueryService queryService;
    public TeamsController(TeamsQueryService queryService) { this.queryService = queryService; }
    @GetMapping("/by-idea/{ideaId}")
    public ResponseEntity<?> getByIdea(@PathVariable Long ideaId) {
        var team = queryService.handle(new GetTeamByIdeaIdQuery(ideaId));
        if (team.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(TeamResourceFromEntityAssembler.toResourceFromEntity(team.get()));
    }
}
