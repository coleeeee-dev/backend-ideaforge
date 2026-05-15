package com.ideaforge.platform.exploration.interfaces.rest.transform;

import com.ideaforge.platform.exploration.domain.model.aggregates.SavedIdea;
import com.ideaforge.platform.exploration.interfaces.rest.resources.SavedIdeaResource;

public class SavedIdeaResourceFromEntityAssembler { public static SavedIdeaResource toResourceFromEntity(SavedIdea e) { return new SavedIdeaResource(e.getId(), e.getProfileId(), e.getIdeaId()); } }
