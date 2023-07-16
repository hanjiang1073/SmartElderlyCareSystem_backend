package com.example.crud3.py;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Python {

    public static Map pingPython(ArrayList<String> old, ArrayList<String> oldfeature, ArrayList<String> volunteer, ArrayList<String> volunteerfeacture, String imageurl, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        // i="C:\\Users\\Lenovo\\Desktop\\b1.jpg";
        Map requestBody = new HashMap();
        requestBody.put("olds",old);
        requestBody.put("oldfeatures",oldfeature);
        requestBody.put("volunteers",volunteer);
        requestBody.put("volunteerfeatures",volunteerfeacture);
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
       // String requestBody = i;
        System.out.println(requestBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> objectResponseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity, Object.class);
        System.out.println("消息响应内容：" + objectResponseEntity.getBody());
        return (Map)objectResponseEntity.getBody();
    }


}
