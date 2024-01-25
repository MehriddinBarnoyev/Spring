package com.mohirdev.mohirdev.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.security.Key;
import java.security.Signature;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private long tokenValidateMilliSecondRemember;
    private long tokenValidateMilliSeconds;
    private final Key key;
    private final JwtParser jwtParser;

    public TokenProvider() {
        byte[] keyByte;
        String secret = "VGhpcyBpcyBhIGJhc2U2NC1lbmNvZGluZyBzZWNyZXQga2V5";
        keyByte = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyByte);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidateMilliSecondRemember = 1000 * 86400;
        this.tokenValidateMilliSeconds = 1000 * 3600;
    }

    public Jws<Claims> parseToken(String token) {
        return jwtParser.parseClaimsJws(token);
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validate;
        if (rememberMe ){
            validate = new Date(now + tokenValidateMilliSecondRemember);
        }else {
            validate = new Date(now + tokenValidateMilliSeconds);
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .signWith(key,SignatureAlgorithm.HS512)
                .setExpiration(validate)
                .compact();
    }
    public Authentication getAuthentacion(String jwt) {
        Claims claims = jwtParser.parseClaimsJwt(jwt).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("auth").toString().split(","))
                .filter(auth-> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return  new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }
    public boolean validateToken(String jwt) {
        try {
            jwtParser.parseClaimsJwt(jwt);
            return true;
        }catch (ExpiredJwtException e){
            logger.error("ExpiredJWTException");
        }catch (UnsupportedJwtException u){
            logger.error("UnsupportedJwtException");
        }catch (MalformedJwtException e){
            logger.error("MalformedJwtException");
        } catch (SignatureException  e ){
            logger.error("SignatureException");
        }catch (IllegalArgumentException e){
            logger.error("IllegalArgumentException");
        }
        return false;
    }

//    public Authentication getAuthentacion(String jwt) {
//    }
}
