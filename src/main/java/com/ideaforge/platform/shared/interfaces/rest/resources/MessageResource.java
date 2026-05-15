package com.ideaforge.platform.shared.interfaces.rest.resources;

public record MessageResource(String message) {
    public static MessageResource of(String message) { return new MessageResource(message); }
}
