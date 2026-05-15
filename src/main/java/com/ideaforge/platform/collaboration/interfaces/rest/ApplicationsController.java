package com.ideaforge.platform.collaboration.interfaces.rest;

import com.ideaforge.platform.collaboration.domain.model.commands.AcceptApplicationCommand;
import com.ideaforge.platform.collaboration.domain.model.commands.RejectApplicationCommand;
import com.ideaforge.platform.collaboration.domain.model.queries.*;
import com.ideaforge.platform.collaboration.domain.services.*;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.ApplyToIdeaResource;
import com.ideaforge.platform.collaboration.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Applications", description = "Project application endpoints")
public class ApplicationsController {
    private final ApplicationsCommandService commandService;
    private final ApplicationsQueryService queryService;
    public ApplicationsController(ApplicationsCommandService commandService, ApplicationsQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }
    @PostMapping("/ideas/{ideaId}/applications")
    public ResponseEntity<?> apply(@PathVariable Long ideaId, @Valid @RequestBody ApplyToIdeaResource resource) { var app = commandService.handle(ApplyToIdeaCommandFromResourceAssembler.toCommandFromResource(ideaId, resource)).orElseThrow(); return ResponseEntity.status(HttpStatus.CREATED).body(ProjectApplicationResourceFromEntityAssembler.toResourceFromEntity(app)); }
    @GetMapping("/ideas/{ideaId}/applications")
    public ResponseEntity<?> getByIdea(@PathVariable Long ideaId) { return ResponseEntity.ok(queryService.handle(new GetApplicationsByIdeaIdQuery(ideaId)).stream().map(ProjectApplicationResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @GetMapping("/applications/by-applicant/{applicantProfileId}")
    public ResponseEntity<?> getByApplicant(@PathVariable Long applicantProfileId) { return ResponseEntity.ok(queryService.handle(new GetApplicationsByApplicantIdQuery(applicantProfileId)).stream().map(ProjectApplicationResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @PostMapping("/applications/{applicationId}/accept")
    public ResponseEntity<?> accept(@PathVariable Long applicationId) { return ResponseEntity.ok(ProjectApplicationResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(new AcceptApplicationCommand(applicationId)).orElseThrow())); }
    @PostMapping("/applications/{applicationId}/reject")
    public ResponseEntity<?> reject(@PathVariable Long applicationId) { return ResponseEntity.ok(ProjectApplicationResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(new RejectApplicationCommand(applicationId)).orElseThrow())); }
}
