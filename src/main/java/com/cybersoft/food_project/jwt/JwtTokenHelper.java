package com.cybersoft.food_project.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenHelper {

    private long expiredDate = 8 * 60 * 60 * 1000;
    private String strKey = "VnUgUGhhbSBLZW4gUGhhbSBKb3NpZSBMdW9uZyBQaHVvbmcgTHVvbmcgVHJhbmcgUGhhbiBCYW8gUGhhbQ=="; //String Base64

    public String generateToken(String data){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + expiredDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        return Jwts.builder()
                .setSubject(data) //store data in token (String)
                .setIssuedAt(now) //time creates token
                .setExpiration(dateExpired) //timeout
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String decodeToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
