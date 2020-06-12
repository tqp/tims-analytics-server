package com.timsanalytics.auth.authCommon.services;

import com.timsanalytics.auth.authCommon.beans.Role;
import com.timsanalytics.auth.authCommon.beans.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final Environment environment;
    private final UserService userService;

    @Autowired
    public TokenService(Environment environment, UserService userService) {
        this.environment = environment;
        this.userService = userService;
    }

    // GENERATE TOKEN
    public String generateTokenFromUser(User user) {
        this.logger.debug("TokenService -> generateTokenFromUser: userGuid=" + user.getUserGuid());
        String authorities = null;

        if (user.getRoles() != null && user.getRoles().size() > 0) {
            authorities = user.getRoles()
                    .stream()
                    .filter(r -> "Active".equalsIgnoreCase(r.getStatus()))
                    .map(Role::getAuthority)
                    .collect(Collectors.joining(","));
            this.logger.debug("Authorities: " + authorities);
        }
        long ACCESS_TOKEN_VALIDITY_SECONDS = Long.parseLong(environment.getProperty("jwt.validity"));
        String SIGNING_KEY = environment.getProperty("jwt.signing-key");
        String AUTHORITIES_KEY = environment.getProperty("jwt.authorities-key");

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuer("TQP")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();
    }

    private String generateTokenFromClaims(Map<String, Object> claims) {
        long ACCESS_TOKEN_VALIDITY_SECONDS = Long.parseLong(environment.getProperty("jwt.validity"));
        String SIGNING_KEY = environment.getProperty("jwt.signing-key");

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();
    }

    // INTERROGATE TOKEN
    public UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final User user) {
        String SIGNING_KEY = environment.getProperty("jwt.signing-key");
        String AUTHORITIES_KEY = environment.getProperty("jwt.authorities-key");

        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        String SIGNING_KEY = environment.getProperty("jwt.signing-key");
        try {
            return Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            this.logger.error("The token has expired. " + e);
        } catch (SignatureException e) {
            this.logger.error("Signature exception. " + e);
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
        }
        return null;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.put("created", new Date(System.currentTimeMillis()));
            refreshedToken = this.generateTokenFromClaims(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // GET USER INFO
    public User getUserFromRequest(HttpServletRequest request) {
        if (request != null) {
            String token = request.getHeader(environment.getProperty("jwt.header")).substring(7);
            String username = getUsernameFromToken(token);
            return userService.getUser(userService.getUserGuidByUsername(username));
        } else {
            return null;
        }
    }
}
