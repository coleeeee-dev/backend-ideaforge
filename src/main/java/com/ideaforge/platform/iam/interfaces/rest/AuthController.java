package com.ideaforge.platform.iam.interfaces.rest;

import com.ideaforge.platform.iam.domain.exceptions.AccountNotFoundException;
import com.ideaforge.platform.iam.domain.exceptions.InvalidCredentialsException;
import com.ideaforge.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.ideaforge.platform.iam.domain.services.AccountsCommandService;
import com.ideaforge.platform.iam.domain.services.AuthService;
import com.ideaforge.platform.iam.interfaces.rest.resources.ChangePasswordResource;
import com.ideaforge.platform.iam.interfaces.rest.resources.LoginResource;
import com.ideaforge.platform.iam.interfaces.rest.resources.RegisterAccountResource;
import com.ideaforge.platform.iam.interfaces.rest.transform.AccountResourceFromEntityAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.LoginCommandFromResourceAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.LoginResponseResourceFromEntityAssembler;
import com.ideaforge.platform.iam.interfaces.rest.transform.RegisterAccountCommandFromResourceAssembler;
import com.ideaforge.platform.shared.interfaces.rest.resources.MessageResource;
import com.ideaforge.platform.shared.security.JwtService;
import com.ideaforge.platform.shared.security.JwtValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Registration and login endpoints")
public class AuthController {
    private final AuthService authService;
    private final AccountsCommandService accountsCommandService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            AccountsCommandService accountsCommandService,
            JwtService jwtService) {
        this.authService = authService;
        this.accountsCommandService = accountsCommandService;
        this.jwtService = jwtService;
    }

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

    @PostMapping("/change-password")
    @Operation(
            summary = "Change the authenticated account password",
            description = "Requires Authorization: Bearer <token> from the login endpoint.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<MessageResource> changePassword(
            @Parameter(hidden = true)
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
            @Valid @RequestBody ChangePasswordResource resource) {
        if (authorization == null || !authorization.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResource.of("Authorization header with Bearer token is required"));
        }

        String token = authorization.substring(7).trim();
        final Long accountId;
        try {
            accountId = jwtService.extractAccountId(token);
        } catch (JwtValidationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResource.of(exception.getMessage()));
        }

        try {
            accountsCommandService.handle(
                    new ChangePasswordCommand(accountId, resource.currentPassword(), resource.newPassword()));
            return ResponseEntity.ok(MessageResource.of("Password updated"));
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResource.of("Current password is incorrect"));
        } catch (AccountNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(MessageResource.of("Account associated with token was not found"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResource> logout() { return ResponseEntity.ok(MessageResource.of("Session closed")); }

    @PostMapping("/password-recovery")
    public ResponseEntity<MessageResource> passwordRecovery(@RequestParam String email) { return ResponseEntity.ok(MessageResource.of("Password recovery requested for " + email)); }
}
