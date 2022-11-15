package com.cybersoft.food_project.security;

import com.cybersoft.food_project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        boolean isSuccess = loginService.checkLogin(userName, password);

        if(isSuccess){
            return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
        }
        else {
            return null;
        }

//        System.out.println("Username" + userName + " " + "Password" + password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
