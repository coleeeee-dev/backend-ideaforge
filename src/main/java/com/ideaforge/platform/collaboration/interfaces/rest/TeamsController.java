package com.ideaforge.platform.collaboration.interfaces.rest;

import com.ideaforge.platform.collaboration.domain.model.commands.RemoveTeamMemberCommand;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamContactsByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.model.queries.GetTeamByIdeaIdQuery;
import com.ideaforge.platform.collaboration.domain.services.TeamsCommandService;
import com.ideaforge.platform.collaboration.domain.services.TeamsQueryService;
import com.ideaforge.platform.collaboration.interfaces.rest.transform.TeamContactResourceFromValueObjectAssembler;
import com.ideaforge.platform.collaboration.interfaces.rest.transform.TeamResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Teams", description = "Team endpoints")
public class TeamsController {
    private final TeamsCommandService commandService;
    private final TeamsQueryService queryService;
    public TeamsController(TeamsCommandService commandService, TeamsQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }
    @GetMapping("/teams/by-idea/{ideaId}")
    public ResponseEntity<?> getByIdea(@PathVariable Long ideaId) {
        var team = queryService.handle(new GetTeamByIdeaIdQuery(ideaId));
        if (team.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(TeamResourceFromEntityAssembler.toResourceFromEntity(team.get()));
    }
    @Operation(summary = "List phone contacts for an idea team")
    @GetMapping("/ideas/{ideaId}/contacts")
    public ResponseEntity<?> getContacts(@PathVariable Long ideaId, @RequestParam Long viewerProfileId) {
        return ResponseEntity.ok(queryService.handle(new GetTeamContactsByIdeaIdQuery(ideaId, viewerProfileId)).stream().map(TeamContactResourceFromValueObjectAssembler::toResourceFromValueObject).toList());
    }
    @Operation(summary = "Remove an active member from a team as the idea owner")
    @DeleteMapping("/teams/{teamId}/members/{memberId}")
    public ResponseEntity<?> removeMember(@PathVariable Long teamId, @PathVariable Long memberId, @RequestParam Long ownerProfileId) {
        var team = commandService.handle(new RemoveTeamMemberCommand(teamId, memberId, ownerProfileId)).orElseThrow();
        return ResponseEntity.ok(TeamResourceFromEntityAssembler.toResourceFromEntity(team));
    }
}
