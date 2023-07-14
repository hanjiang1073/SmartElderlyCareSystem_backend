package com.example.crud3.py;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Python {
    public static Map pingPython(Object olds,Object volunteers,String imageurl, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        // i="C:\\Users\\Lenovo\\Desktop\\b1.jpg";
        Map requestBody = new HashMap();
        requestBody.put("olds",olds);
        requestBody.put("volunteers",volunteers);
        requestBody.put("image",imageurl);
        System.out.println(requestBody);
        HttpEntity<Map> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> objectResponseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity, Object.class);
        System.out.println("消息响应内容：" + objectResponseEntity.getBody());
        return (Map)objectResponseEntity.getBody();
    }
    public Map pingPython(String i, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
       // i="C:\\Users\\Lenovo\\Desktop\\b1.jpg";
        String requestBody = "{\"image\":\"" + i.replace("\\", "\\\\") + "\"}";
        System.out.println(requestBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> objectResponseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity, Object.class);
        System.out.println("消息响应内容：" + objectResponseEntity.getBody());
        return (Map)objectResponseEntity.getBody();
    }


}
