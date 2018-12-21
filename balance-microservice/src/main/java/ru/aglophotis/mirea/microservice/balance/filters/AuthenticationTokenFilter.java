package ru.aglophotis.mirea.microservice.balance.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import ru.aglophotis.mirea.microservice.balance.entities.Payload;
import ru.aglophotis.mirea.microservice.balance.services.CustomUserDetailsService;
import ru.aglophotis.mirea.microservice.balance.utils.TokenUtils;

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

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        String token = request.getHeader(tokenHeader);
//        if (token == null)
//            token = request.getParameter("token");
//        if (token == null) {
//            TokenAuthentication authentication = new TokenAuthentication(null);
//            authentication.setAuthenticated(false);
//            return authentication;
//        }
//        TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
//        Authentication authentication = getAuthenticationManager().authenticate(tokenAuthentication);
//        return authentication;
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        Payload payload = tokenUtils.getPayload(authToken);

        if (payload.getName() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(payload.getName());
            if (this.tokenUtils.validateToken(authToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("TEST");
            }
        }

        chain.doFilter(request, response);
    }
}
