package com.ideaforge.platform.profiles.interfaces.rest.resources;

import jakarta.validation.constraints.Size;

public record UpdateContactSettingsResource(@Size(max = 20) String phoneNumber, boolean sharePhoneWithTeam) { }
