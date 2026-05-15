package com.ideaforge.platform.moderation.interfaces.rest;

import com.ideaforge.platform.moderation.domain.model.queries.*;
import com.ideaforge.platform.moderation.domain.services.ReportsCommandService;
import com.ideaforge.platform.moderation.domain.services.ReportsQueryService;
import com.ideaforge.platform.moderation.interfaces.rest.resources.ResolveReportResource;
import com.ideaforge.platform.moderation.interfaces.rest.transform.ReportResourceFromEntityAssembler;
import com.ideaforge.platform.moderation.interfaces.rest.transform.ResolveReportCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/moderation/reports")
@Tag(name = "Moderation", description = "Admin moderation endpoints")
public class ModerationController {
    private final ReportsCommandService commandService;
    private final ReportsQueryService queryService;
    public ModerationController(ReportsCommandService commandService, ReportsQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }
    @GetMapping
    public ResponseEntity<?> getReports(@RequestParam(required = false) String status) { var reports = status == null ? queryService.handle(new GetAllReportsQuery()) : queryService.handle(new GetReportsByStatusQuery(status)); return ResponseEntity.ok(reports.stream().map(ReportResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @PostMapping("/{reportId}/resolve")
    public ResponseEntity<?> resolve(@PathVariable Long reportId, @Valid @RequestBody ResolveReportResource resource) { return ResponseEntity.ok(ReportResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(ResolveReportCommandFromResourceAssembler.toCommandFromResource(reportId, resource)).orElseThrow())); }
}
