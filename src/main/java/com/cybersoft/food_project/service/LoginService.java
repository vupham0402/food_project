package com.cybersoft.food_project.service;

import com.cybersoft.food_project.entity.UserEntity;

import java.util.List;

public interface LoginService {
    boolean checkLogin(String email, String password);
}
