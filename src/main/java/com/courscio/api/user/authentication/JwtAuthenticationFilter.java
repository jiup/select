package com.courscio.api.user.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.courscio.api.user.authentication.JwtConstant.*;

/**
 * @author Jiupeng Zhang
 * @since 03/25/2019
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authenticationHeader = request.getHeader(HEADER);

        if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response, authenticationHeader.substring(TOKEN_PREFIX.length()));
        if (authentication == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal Token");
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response, String token) {
        try {
            Claims claims = Jwts.parser()
                    .requireSubject(JwtConstant.SUBJECT)
                    .requireIssuer(JwtConstant.ISSUER)
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            if (!validateClaims(claims, request)) {
                LOG.warn("illegal token {} found and removed.", claims.toString());
                response.setHeader(HEADER, null);
                return null;
            }

            return new UsernamePasswordAuthenticationToken(claims, null, Collections.emptyList());

        } catch (Exception e) {
            LOG.warn("illegal token: jwt parse error ({})", e.getMessage());
            return null;
        }
    }

    private boolean validateClaims(Claims claims, HttpServletRequest request) {
        if (!claims.getOrDefault("role", "undefined").equals("student")) {
            LOG.warn("illegal token: role ({})", claims.getOrDefault("role", "undefined"));
            return false;
        }

        if (!claims.getOrDefault("ua", "undefined").equals(request.getHeader("user-agent"))) {
            LOG.warn("illegal token: user-agent ({})", claims.getOrDefault("ua", "undefined"));
            return false;
        }

        if (!claims.getOrDefault("ip", "undefined").equals(request.getRemoteAddr())) {
            LOG.warn("illegal token: ip ({})", claims.getOrDefault("ip", "undefined"));
            return false;
        }

        return true;
    }
}