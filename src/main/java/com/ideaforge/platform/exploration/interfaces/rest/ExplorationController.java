package com.ideaforge.platform.exploration.interfaces.rest;

import com.ideaforge.platform.exploration.domain.model.queries.GetRecommendedIdeasQuery;
import com.ideaforge.platform.exploration.domain.model.queries.SearchIdeasQuery;
import com.ideaforge.platform.exploration.domain.services.ExplorationQueryService;
import com.ideaforge.platform.exploration.interfaces.rest.transform.IdeaViewResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exploration/ideas")
@Tag(name = "Exploration", description = "Idea search and discovery endpoints")
public class ExplorationController {
    private final ExplorationQueryService queryService;
    public ExplorationController(ExplorationQueryService queryService) { this.queryService = queryService; }
    @GetMapping
    public ResponseEntity<?> search(@RequestParam(required = false) String keyword, @RequestParam(required = false) String category, @RequestParam(required = false) String status, @RequestParam(required = false) String stage) { return ResponseEntity.ok(queryService.handle(new SearchIdeasQuery(keyword, category, status, stage)).stream().map(IdeaViewResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @GetMapping("/search")
    public ResponseEntity<?> searchAlias(@RequestParam(required = false) String keyword, @RequestParam(required = false) String category, @RequestParam(required = false) String status, @RequestParam(required = false) String stage) { return search(keyword, category, status, stage); }
    @GetMapping("/recommended")
    public ResponseEntity<?> recommended(@RequestParam(required = false) Long profileId) { return ResponseEntity.ok(queryService.handle(new GetRecommendedIdeasQuery(profileId)).stream().map(IdeaViewResourceFromEntityAssembler::toResourceFromEntity).toList()); }
}
