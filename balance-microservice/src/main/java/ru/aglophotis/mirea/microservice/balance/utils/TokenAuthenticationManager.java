//package ru.aglophotis.mirea.microservice.balance.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import ru.aglophotis.mirea.microservice.balance.entities.Payload;
//import ru.aglophotis.mirea.microservice.balance.services.CustomUserDetailsService;
//
//import java.util.Collection;
//
//@Service
//public class TokenAuthenticationManager implements AuthenticationManager {
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        if (authentication instanceof TokenAuthentication) {
//            TokenAuthentication readyTokenAuthentication = processAuthentication((TokenAuthentication) authentication);
//            return readyTokenAuthentication;
//        } else {
//            authentication.setAuthenticated(false);
//            return authentication;
//        }
//    }
//
//    private TokenAuthentication processAuthentication(TokenAuthentication authentication) throws AuthenticationException {
//        String token = authentication.getToken();
//        TokenUtils tokenUtils = new TokenUtils();
//        if (!tokenUtils.validateToken(token))
//            throw new AuthenticationServiceException("Invalid token");
//        return buildFullTokenAuthentication(authentication, tokenUtils.getPayload(token));
//    }
//
//    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, Payload payload) {
//        UserDetails user = userDetailsService.loadUserByUsername(payload.getName());
//        if (user.isEnabled()) {
//            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//            TokenAuthentication fullTokenAuthentication =
//                    new TokenAuthentication(authentication.getToken(), authorities, true, user);
//            return fullTokenAuthentication;
//        } else {
//            throw new AuthenticationServiceException("User disabled");
//        }
//    }
//}
