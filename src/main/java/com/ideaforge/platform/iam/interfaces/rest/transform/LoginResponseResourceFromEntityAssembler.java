package com.ideaforge.platform.iam.interfaces.rest.transform;

import com.ideaforge.platform.iam.domain.model.valueobjects.LoginResult;
import com.ideaforge.platform.iam.interfaces.rest.resources.LoginResponseResource;

public class LoginResponseResourceFromEntityAssembler { public static LoginResponseResource toResourceFromEntity(LoginResult entity) { return new LoginResponseResource(entity.accountId(), entity.email(), entity.role().name(), entity.token()); } }
