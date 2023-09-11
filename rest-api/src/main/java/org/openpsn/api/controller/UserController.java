package org.openpsn.api.controller;

import io.jooby.Context;
import io.jooby.annotation.GET;
import io.jooby.annotation.Path;
import io.jooby.annotation.PathParam;
import lombok.RequiredArgsConstructor;
import org.openpsn.api.model.User;
import org.openpsn.api.service.UserService;
import org.pac4j.core.profile.CommonProfile;

import javax.inject.Inject;
import javax.inject.Singleton;


@Path("/api/v1/users")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class UserController {
    private final UserService userService;

    @GET
    @Path("/{psnName}")
    public User getUser(@PathParam String psnName, Context ctx) {
        if ("@me".equals(psnName)) {
            psnName = ctx.<CommonProfile>getUser().getUsername();
        }
        return userService.getUser(psnName);
    }
}
