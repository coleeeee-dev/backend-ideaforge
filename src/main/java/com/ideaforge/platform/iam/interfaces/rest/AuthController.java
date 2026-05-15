package com.ideaforge.platform.iam.interfaces.rest;

import com.ideaforge.platform.iam.domain.services.AuthService;
import com.ideaforge.platform.iam.interfaces.rest.resources.LoginResource;
import com.ideaforge.platform.iam.interfaces.rest.resources.RegisterAccountResource;
import com.ideaforge.platform.iam.interfaces.rest.transform.AccountResourceFromEntityAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.LoginCommandFromResourceAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.LoginResponseResourceFromEntityAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.RegisterAccountCommandFromResourceAssembler;
import com.ideaforge.platform.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Registration and login endpoints")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    @Operation(summary = "Register a new account")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterAccountResource resource) {
        var command = RegisterAccountCommandFromResourceAssembler.toCommandFromResource(resource);
        var account = authService.handle(command).orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResourceFromEntityAssembler.toResourceFromEntity(account));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<?> login(@Valid @RequestBody LoginResource resource) {
        var result = authService.handle(LoginCommandFromResourceAssembler.toCommandFromResource(resource)).orElseThrow();
        return ResponseEntity.ok(LoginResponseResourceFromEntityAssembler.toResourceFromEntity(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResource> logout() { return ResponseEntity.ok(MessageResource.of("Session closed")); }

    @PostMapping("/password-recovery")
    public ResponseEntity<MessageResource> passwordRecovery(@RequestParam String email) { return ResponseEntity.ok(MessageResource.of("Password recovery requested for " + email)); }
}
