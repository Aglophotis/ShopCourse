package ru.aglophotis.mirea.microservice.item.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.aglophotis.mirea.microservice.item.entities.Payload;
import ru.aglophotis.mirea.microservice.item.services.CustomUserDetailsService;
import ru.aglophotis.mirea.microservice.item.utils.TokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private String tokenHeader = "Authorization";

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        System.out.println(authToken);
        if (authToken != null) {
            Payload payload = tokenUtils.getPayload(authToken);
            if (payload != null && payload.getName() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(payload.getName());
                System.out.println(userDetails);
                if (this.tokenUtils.validateToken(authToken)) {
                    System.out.println("TOKEN IS VALIIID");
                    UsernamePasswordAuthenticationToken authReq
                            = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    Authentication auth = getAuthenticationManager().authenticate(authReq);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
