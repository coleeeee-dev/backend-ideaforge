package com.ideaforge.platform.exploration.interfaces.rest.transform;

import com.ideaforge.platform.exploration.domain.model.valueobjects.IdeaView;
import com.ideaforge.platform.exploration.interfaces.rest.resources.IdeaViewResource;

public class IdeaViewResourceFromEntityAssembler { public static IdeaViewResource toResourceFromEntity(IdeaView v) { return new IdeaViewResource(v.id(), v.creatorProfileId(), v.title(), v.shortDescription(), v.category(), v.status(), v.stage(), v.collaborationMode()); } }
