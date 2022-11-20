package com.cybersoft.food_project.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper {

    private final String strKey = "VnUgUGhhbSBLZW4gUGhhbSBKb3NpZSBMdW9uZyBQaHVvbmcgTHVvbmcgVHJhbmcgUGhhbiBCYW8gUGhhbQ=="; //String Base64
    private Gson gson = new Gson();

    public String generateToken(String data, String type, long expiredDate){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + expiredDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        Map<String, Object> subjectData = new HashMap<>();
        subjectData.put("username", data);
        subjectData.put("type", type);

        String json = gson.toJson(subjectData);

        return Jwts.builder()
                .setSubject(json) //store data in token (String)
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

    public boolean validateToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        boolean isSuccess = false;
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            isSuccess = true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return isSuccess;
    }
}
