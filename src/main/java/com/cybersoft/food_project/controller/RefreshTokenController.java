package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.jwt.JwtTokenHelper;
import com.cybersoft.food_project.payload.response.DataResponse;
import com.cybersoft.food_project.payload.response.DataTokenResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/refresh-token")
public class RefreshTokenController {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    private Gson gson = new Gson();
    private long expiredDate = 8 * 60 * 60 * 1000;
    private long refreshExpiredDate = 80 * 60 * 60 * 1000;

    @PostMapping("")
    public ResponseEntity<?> index(@RequestParam("token") String token){
        DataResponse dataResponse = new DataResponse();
        if(jwtTokenHelper.validateToken(token)){
            String json = jwtTokenHelper.decodeToken(token);
            Map<String, Object> map = gson.fromJson(json, Map.class);
            if (StringUtils.hasText(map.get("type").toString()) && map.get("type").toString().equals("refresh")){
                String tokenAuthen = jwtTokenHelper.generateToken(map.get("username").toString(),"authen", expiredDate);
                String refreshToken = jwtTokenHelper.generateToken(map.get("username").toString(),"refresh", refreshExpiredDate);
                DataTokenResponse dataTokenResponse = new DataTokenResponse();
                dataTokenResponse.setToken(tokenAuthen);
                dataTokenResponse.setRefreshToken(refreshToken);

                dataResponse.setStatus(HttpStatus.OK.value());
                dataResponse.setSuccess(true);
                dataResponse.setDesc("");
                dataResponse.setData(dataTokenResponse);
            }
            else {
                dataResponse.setStatus(HttpStatus.OK.value());
                dataResponse.setSuccess(true);
                dataResponse.setDesc("");
                dataResponse.setData("");
            }
        }

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
