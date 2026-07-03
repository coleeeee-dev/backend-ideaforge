package com.ideaforge.platform.collaboration.domain.model.commands;

public record RemoveTeamMemberCommand(Long teamId, Long memberId, Long ownerProfileId) { }
