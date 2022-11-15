package com.cybersoft.food_project.security;

import com.cybersoft.food_project.entity.UserEntity;
import com.cybersoft.food_project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    LoginService loginService;

    //@Autowired
    //@Qualifer: define which bean will be used
    //PasswordEncoder passwordEncoder;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

//        boolean isSuccess = loginService.checkLogin(userName, password);
//
//        if(isSuccess){
//            return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
//        }
//        else {
//            return null;
//        }

        UserEntity userEntity = loginService.checkLogin(userName);

        if(userEntity != null){
            //System.out.println("Username " + userName + " " + "Password " + password + " " + userEntity.getPassword());
            boolean isMatchPassword = passwordEncoder.matches(password, userEntity.getPassword());
            //System.out.println(isMatchPassword);
            if(isMatchPassword){
                return new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
            }
            else{
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
