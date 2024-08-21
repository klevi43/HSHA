package org.hsha.hsha.components;

import org.hsha.hsha.models.User;
import org.hsha.hsha.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    UserService userService;
//    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext ctx) {
        // get {userId} from request
        int userId = Integer.parseInt(ctx.getVariables().get("userId"));
        Authentication auth = authenticationSupplier.get();
        try {
            return new AuthorizationDecision(hasUserId(auth, userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasUserId(Authentication authentication, int id) throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int checkedUserId = userService.retrieveUserIdByEmail(userDetails.getUsername());
        if(id == checkedUserId) {
            return true;
        }

        return false;

    }
}
