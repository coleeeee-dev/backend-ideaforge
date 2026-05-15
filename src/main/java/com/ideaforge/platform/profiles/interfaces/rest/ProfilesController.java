package com.ideaforge.platform.profiles.interfaces.rest;

import com.ideaforge.platform.profiles.domain.model.queries.*;
import com.ideaforge.platform.profiles.domain.services.ProfilesCommandService;
import com.ideaforge.platform.profiles.domain.services.ProfilesQueryService;
import com.ideaforge.platform.profiles.interfaces.rest.resources.*;
import com.ideaforge.platform.profiles.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profiles", description = "Profile, skills and interests endpoints")
public class ProfilesController {
    private final ProfilesCommandService commandService;
    private final ProfilesQueryService queryService;
    public ProfilesController(ProfilesCommandService commandService, ProfilesQueryService queryService) { this.commandService = commandService; this.queryService = queryService; }

    @PostMapping
    public ResponseEntity<?> createProfile(@Valid @RequestBody CreateProfileResource resource) {
        var profile = commandService.handle(CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile));
    }
    @GetMapping
    public ResponseEntity<?> getAllProfiles() { return ResponseEntity.ok(queryService.handle(new GetAllProfilesQuery()).stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList()); }
    @GetMapping("/{profileId}")
    public ResponseEntity<?> getProfileById(@PathVariable Long profileId) {
        var profile = queryService.handle(new GetProfileByIdQuery(profileId));
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get()));
    }
    @GetMapping("/by-account/{accountId}")
    public ResponseEntity<?> getProfileByAccountId(@PathVariable Long accountId) {
        var profile = queryService.handle(new GetProfileByAccountIdQuery(accountId));
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get()));
    }
    @PutMapping("/{profileId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long profileId, @Valid @RequestBody UpdateProfileResource resource) { return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource)).orElseThrow())); }
    @PutMapping("/{profileId}/skills")
    public ResponseEntity<?> updateSkills(@PathVariable Long profileId, @RequestBody UpdateProfileSkillsResource resource) { return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(UpdateProfileSkillsCommandFromResourceAssembler.toCommandFromResource(profileId, resource)).orElseThrow())); }
    @PutMapping("/{profileId}/interests")
    public ResponseEntity<?> updateInterests(@PathVariable Long profileId, @RequestBody UpdateProfileInterestsResource resource) { return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(commandService.handle(UpdateProfileInterestsCommandFromResourceAssembler.toCommandFromResource(profileId, resource)).orElseThrow())); }
}
