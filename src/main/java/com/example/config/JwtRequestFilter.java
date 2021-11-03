package com.example.config;

import com.example.service.BlackListService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.resource.AuthorizationResource;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private BlackListService blackListService;
    /*
    @Autowired
    private JwtTokenService jwtTokenService;
    */

    public JwtRequestFilter(BlackListService blackListService){
        this.blackListService = blackListService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("request uri: " + request.getRequestURI() + " method: " + request.getMethod());
        AuthzClient authzClient = AuthzClient.create();
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        AuthorizationResponse authorizationResponse = authzClient.authorization(getJwtFromRequest(request).get()).authorize(authorizationRequest);
        String rpt = authorizationResponse.getToken();
        System.out.println("You got an RPT: " + rpt);


        final Optional<String> jwt = getJwtFromRequest(request);
        jwt.ifPresent(token -> {
            try {
                var value = blackListService.find(token);
                /*
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                */
                //if (jwtTokenService.validateToken(token)) {
                if(StringUtils.isNotEmpty(value)){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not authorized");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    //setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
                    throw new IllegalArgumentException();
                }
            } catch (IOException | IllegalArgumentException | MalformedJwtException | ExpiredJwtException e) {
                logger.error("Unable to get JWT Token or JWT Token has expired");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("anonymous", "anonymous", null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        });

        filterChain.doFilter(request, response);
    }

    /*
    private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
        final String username = jwtTokenService.getUsernameFromToken(token);
        final List<String> roles = jwtTokenService.getRoles(token);
        final UserDetails userDetails = new User(username, "", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(authDetails);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    */

    private static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

}
