package com.team4.dayoff.api.loginAPI;

import java.util.Map;

import com.team4.dayoff.entity.Users;

/**
 * LoginAPI
 */
public interface LoginAPI {
    Map<String,String> getToken(String code);
    Users getUserInfo(String accessToken);
    int withdrawUser(String accessToken);
    //int logoutUser(String accessToken);
    //Map<String,String> renewAccessToken(String accessToken);
    
}