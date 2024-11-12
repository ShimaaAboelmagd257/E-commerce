package com.techie.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final Log log = LogFactory.getLog(JWTAuthenticationFilter.class);

    private JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;


    @Autowired
    public JWTAuthenticationFilter(@Lazy JwtTokenProvider jwtTokenProvider, @Lazy UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
           throws ServletException, IOException {

       String jwt = getJwtFromRequest(request);
       if (jwt == null) {
           log.info("No JWT found in the request headers.");
       } else {
           log.info("JWT found: " + jwt);
       }
       if (StringUtils.hasText(jwt) && jwtTokenProvider.verifyToken(jwt)) {
           Authentication auth = jwtTokenProvider.getAuthentication(jwt);
           SecurityContextHolder.getContext().setAuthentication(auth);
           log.info("Authentication set in SecurityContextHolder.");
       } else {
           log.info("Invalid JWT or no authentication set.");
       }

       filterChain.doFilter(request, response);
   }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("doFilterInternal - getJwtFromRequest - token passed ");
            return bearerToken.substring(7);
        }
        return null;
    }
}
