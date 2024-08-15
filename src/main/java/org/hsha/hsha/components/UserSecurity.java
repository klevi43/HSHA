package org.hsha.hsha.components;

import org.hsha.hsha.models.User;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext ctx) {
        // get {userId} from request
        int userId = Integer.parseInt(ctx.getVariables().get("email"));
        Authentication auth = authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(auth, userId));
    }

    public boolean hasUserId(Authentication authentication, int id) {
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getId());
        if (user.getId() == id ) {
            return true;
        }

        return false;

    }
}
