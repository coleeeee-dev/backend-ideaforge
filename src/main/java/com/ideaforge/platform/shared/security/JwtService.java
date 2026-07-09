package com.ideaforge.platform.shared.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() { };

    private final ObjectMapper objectMapper;
    private final SecretKeySpec signingKey;
    private final long expirationMinutes;

    public JwtService(
            ObjectMapper objectMapper,
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret must not be blank");
        }
        if (expirationMinutes <= 0) {
            throw new IllegalStateException("JWT expiration must be greater than zero");
        }
        this.objectMapper = objectMapper;
        this.signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(Long accountId, String email) {
        if (accountId == null || accountId <= 0) {
            throw new IllegalArgumentException("Account id must be a positive number");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }

        long issuedAt = Instant.now().getEpochSecond();
        long expiresAt;
        try {
            expiresAt = Math.addExact(issuedAt, Math.multiplyExact(expirationMinutes, 60));
        } catch (ArithmeticException exception) {
            throw new IllegalStateException("JWT expiration is too large", exception);
        }

        var header = new LinkedHashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        var claims = new LinkedHashMap<String, Object>();
        claims.put("sub", accountId.toString());
        claims.put("accountId", accountId);
        claims.put("email", email);
        claims.put("iat", issuedAt);
        claims.put("exp", expiresAt);

        String encodedHeader = encodeJson(header);
        String encodedClaims = encodeJson(claims);
        String unsignedToken = encodedHeader + "." + encodedClaims;
        return unsignedToken + "." + encode(sign(unsignedToken));
    }

    public Long extractAccountId(String token) {
        Map<String, Object> claims = validateAndExtractClaims(token);
        Object accountIdClaim = claims.get("accountId");
        if (!(accountIdClaim instanceof Number accountIdNumber)) {
            throw new JwtValidationException("Token does not contain a valid accountId");
        }

        long accountId = accountIdNumber.longValue();
        if (accountId <= 0 || !Long.toString(accountId).equals(asRequiredString(claims.get("sub"), "subject"))) {
            throw new JwtValidationException("Token contains invalid account identity claims");
        }
        return accountId;
    }

    public long getExpirationMinutes() {
        return expirationMinutes;
    }

    private Map<String, Object> validateAndExtractClaims(String token) {
        if (token == null || token.isBlank()) {
            throw new JwtValidationException("Token must not be blank");
        }

        String[] parts = token.split("\\.", -1);
        if (parts.length != 3 || parts[0].isBlank() || parts[1].isBlank() || parts[2].isBlank()) {
            throw new JwtValidationException("Token format is invalid");
        }

        try {
            Map<String, Object> header = decodeJson(parts[0]);
            if (!"HS256".equals(header.get("alg"))) {
                throw new JwtValidationException("Token algorithm is invalid");
            }

            String unsignedToken = parts[0] + "." + parts[1];
            byte[] providedSignature = BASE64_URL_DECODER.decode(parts[2]);
            if (!MessageDigest.isEqual(sign(unsignedToken), providedSignature)) {
                throw new JwtValidationException("Token signature is invalid");
            }

            Map<String, Object> claims = decodeJson(parts[1]);
            Object expirationClaim = claims.get("exp");
            if (!(expirationClaim instanceof Number expiration)) {
                throw new JwtValidationException("Token does not contain a valid expiration");
            }
            if (Instant.now().getEpochSecond() >= expiration.longValue()) {
                throw new JwtValidationException("Token has expired");
            }
            asRequiredString(claims.get("email"), "email");
            return claims;
        } catch (IllegalArgumentException | IOException exception) {
            throw new JwtValidationException("Token format is invalid", exception);
        }
    }

    private String encodeJson(Map<String, Object> value) {
        try {
            return encode(objectMapper.writeValueAsBytes(value));
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to create JWT", exception);
        }
    }

    private Map<String, Object> decodeJson(String value) throws IOException {
        return objectMapper.readValue(BASE64_URL_DECODER.decode(value), MAP_TYPE);
    }

    private byte[] sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(signingKey);
            return mac.doFinal(value.getBytes(StandardCharsets.US_ASCII));
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Unable to sign JWT", exception);
        }
    }

    private String encode(byte[] value) {
        return BASE64_URL_ENCODER.encodeToString(value);
    }

    private String asRequiredString(Object value, String claimName) {
        if (!(value instanceof String stringValue) || stringValue.isBlank()) {
            throw new JwtValidationException("Token does not contain a valid " + claimName);
        }
        return stringValue;
    }
}
