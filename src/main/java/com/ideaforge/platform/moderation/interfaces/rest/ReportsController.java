package com.ideaforge.platform.moderation.interfaces.rest;

import com.ideaforge.platform.moderation.domain.model.queries.GetReportByIdQuery;
import com.ideaforge.platform.moderation.domain.services.ReportsCommandService;
import com.ideaforge.platform.moderation.domain.services.ReportsQueryService;
import com.ideaforge.platform.moderation.interfaces.rest.resources.CreateReportResource;
import com.ideaforge.platform.moderation.interfaces.rest.transform.CreateReportCommandFromResourceAssembler;
import com.ideaforge.platform.moderation.interfaces.rest.transform.ReportResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Reports", description = "User report endpoints")
public class ReportsController {
    private final ReportsCommandService commandService;
    private final ReportsQueryService queryService;
    public ReportsController(ReportsCommandService commandService, ReportsQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateReportResource resource) { var report = commandService.handle(CreateReportCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(ReportResourceFromEntityAssembler.toResourceFromEntity(report)); }
    @GetMapping("/{reportId}")
    public ResponseEntity<?> getById(@PathVariable Long reportId) {
        var report = queryService.handle(new GetReportByIdQuery(reportId));
        if (report.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ReportResourceFromEntityAssembler.toResourceFromEntity(report.get()));
    }
}
