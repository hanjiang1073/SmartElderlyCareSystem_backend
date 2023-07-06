package com.example.crud3.service;


public interface MailService {

    String getCode(String email,int type);

    int sendCodeForFind(String username);

    int sendCodeREGISTER(String username);

    int sendCode(String email,int type);
    void sendSimpleMail(String to, String subject, String content);
}
