package com.timsanalytics.auth.authGoogle.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.auth.authGoogle.beans.TokenExchange;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TokenExchangeService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private static Map<String, String> map = new HashMap<>();
    private final String tokenKey = "tokenKey";

    public String generateShortLivedToken() {
        String shortLivedAuthToken = this.generateToken("token1", "IM", "exchangeToken", 1000000); // Get from yml in future
        this.parseJWT(shortLivedAuthToken); // Test Generated Token
        this.addItem(shortLivedAuthToken, new Timestamp(System.currentTimeMillis()).toString());
        return shortLivedAuthToken;
    }

    public String generateToken(String id, String issuer, String subject, long ttlMillis) {
        // This JWT signature algorithm we will be using to sign the token.
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // If it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return jwtBuilder.compact();
    }

    public void addItem(String key, String value) {
        this.logger.debug("Adding Token Exchange Item: Key=" + shortId(key) + ", Value=" + shortId(value));
        map.put(key, value);
    }

    public void removeItem(String key) {
        this.logger.debug("Removing Token Exchange Item: Key=" + key);
        map.remove(key);
    }

    public String getItem(String key) {
        this.logger.debug("Get Token Exchange Item: Key=" + key + ", Value=" + map.get(key));
        return map.get(key);
    }

    public List<KeyValue> getAllItems() {
        Set<Map.Entry<String, String>> set = map.entrySet();
        List<KeyValue> list = new ArrayList<>();
        for (Object aSet : set) {
            Map.Entry entry = (Map.Entry) aSet;
            KeyValue item = new KeyValue(entry.getKey().toString(), entry.getValue().toString());
            list.add(item);
        }
        this.logger.debug("Get All Token Exchange Items:\n" + list.toString());
        return list;
    }

    public TokenExchange exchangeToken(TokenExchange shortLivedToken) {
        this.logger.debug("TokenExchangeService -> exchangeToken: shortLivedToken=" + shortId(shortLivedToken.getKey()));
        String token;
        token = map.get(shortLivedToken.getKey());
        System.out.println("Token: " + shortId(token));
        TokenExchange tokenExchange = new TokenExchange();
        tokenExchange.setKey(shortLivedToken.getKey());
        tokenExchange.setValue(token);
        return tokenExchange;
    }

    public void parseJWT(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(tokenKey))
                .parseClaimsJws(jwt).getBody();
        this.logger.debug("ID: " + claims.getId());
        this.logger.debug("Subject: " + claims.getSubject());
        this.logger.debug("Issuer: " + claims.getIssuer());
        this.logger.debug("Expiration: " + claims.getExpiration());
    }

    public String shortId(String longId) {
        return longId.substring(longId.length() - 10);
    }
}
