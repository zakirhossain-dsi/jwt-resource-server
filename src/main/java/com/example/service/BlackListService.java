package com.example.service;

public interface BlackListService {

    void saveAccessToken(String accessToken);
    String find(String accessToken);
}

