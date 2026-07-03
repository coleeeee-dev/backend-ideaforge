package com.ideaforge.platform.profiles.domain.model.commands;

public record UpdateContactSettingsCommand(Long profileId, String phoneNumber, boolean sharePhoneWithTeam) { }
