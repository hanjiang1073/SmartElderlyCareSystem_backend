package com.example.crud3.py;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.crud3.entity.ElderEntity;
import com.example.crud3.entity.VolunteerEntity;
import com.example.crud3.service.ElderService;
import com.example.crud3.service.VolunteerService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FlaskAPIClient {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://your-flask-api-endpoint");


        List<VolunteerEntity> volunteerlist = new ArrayList();
        //volunteerlist=v.list();
        String[] v_vector = new String[volunteerlist.size()];
        for(int i=0;i<volunteerlist.size();i++){
            v_vector[i]=volunteerlist.get(i).getVector();
        }


        List<ElderEntity> elderlist = new ArrayList();
        String[] e_vector = new String[elderlist.size()];
        for(int i=0;i<elderlist.size();i++){
            e_vector[i]=elderlist.get(i).getVector();
        }

        try {
            // 构建请求体参数
            JSONObject requestBody = new JSONObject();
            requestBody.put("A", v_vector);
            requestBody.put("B", e_vector);

            // 将请求体参数转换为字符串
            StringEntity entity = new StringEntity(requestBody.toString());

            // 设置请求体
            request.setEntity(entity);

            // 设置请求头
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // 处理响应结果
            System.out.println(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}