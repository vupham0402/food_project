package com.cybersoft.food_project.jwt;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        //System.out.println("Token " + token);
        if(token != null){
            //check if token was generated from our system
            if(jwtTokenHelper.validateToken(token)){
//                String userName = jwtTokenHelper.decodeToken(token);
//                System.out.println(userName);
                String json = jwtTokenHelper.decodeToken(token);
                Map<String, Object> map = gson.fromJson(json, Map.class);
                if (StringUtils.hasText(map.get("type").toString()) && !map.get("type").toString().equals("refresh")){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("","", new ArrayList<>());
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request){
        //get token in authorization from header
        String strToken = request.getHeader("Authorization");
        if(StringUtils.hasText(strToken) && strToken.startsWith("Bearer ")){
            String finalToken = strToken.substring(7);
            return finalToken;
        }
        else {
            return null;
        }
    }
}
