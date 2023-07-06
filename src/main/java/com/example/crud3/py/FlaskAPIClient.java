package com.example.crud3.py;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.crud3.entity.VolunteerEntity;
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
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FlaskAPIClient {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://your-flask-api-endpoint");

        VolunteerService v = new VolunteerService() {
            @Override
            public boolean saveBatch(Collection<VolunteerEntity> entityList, int batchSize) {
                return false;
            }

            @Override
            public boolean saveOrUpdateBatch(Collection<VolunteerEntity> entityList, int batchSize) {
                return false;
            }

            @Override
            public boolean updateBatchById(Collection<VolunteerEntity> entityList, int batchSize) {
                return false;
            }

            @Override
            public boolean saveOrUpdate(VolunteerEntity entity) {
                return false;
            }

            @Override
            public VolunteerEntity getOne(Wrapper<VolunteerEntity> queryWrapper, boolean throwEx) {
                return null;
            }

            @Override
            public Map<String, Object> getMap(Wrapper<VolunteerEntity> queryWrapper) {
                return null;
            }

            @Override
            public <V> V getObj(Wrapper<VolunteerEntity> queryWrapper, Function<? super Object, V> mapper) {
                return null;
            }

            @Override
            public BaseMapper<VolunteerEntity> getBaseMapper() {
                return null;
            }

            @Override
            public Class<VolunteerEntity> getEntityClass() {
                return null;
            }
        };

        try {
            // 构建请求体参数
            JSONObject requestBody = new JSONObject();
            //requestBody.put("A", vector);
            //requestBody.put("B", );

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