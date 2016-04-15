package controllers;

import securesocial.core.java.Authorization;
import service.User;

/**
 * A sample authorization implementation that lets you filter requests based
 * on the provider that authenticated the user
 */
public class WithProvider implements Authorization<User> {
    public boolean isAuthorized(User user, String params[]) {
        return user.main.providerId().equals(params[0]);
    }
}


