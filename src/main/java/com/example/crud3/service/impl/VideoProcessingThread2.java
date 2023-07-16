package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crud3.entity.ElderEntity;
import com.example.crud3.entity.FacetypeEntity;
import com.example.crud3.entity.VolunteerEntity;
import com.example.crud3.mapper.ElderMapper;
import com.example.crud3.mapper.VolunteerMapper;
import com.example.crud3.py.Python;
import com.example.crud3.service.ElderService;
import com.example.crud3.service.VolunteerService;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VideoProcessingThread2 extends  Thread {
    private InitInstance initInstance;
    private Python py;
    private String userId;
    private int type;
    private String type2;
    private List<VolunteerEntity> volunteers;
    private List<ElderEntity> elders;
    ArrayList<String> old = new ArrayList<>();
    ArrayList<String> oldfeatures = new ArrayList<>();
    ArrayList<String> volunteer = new ArrayList<>();
    ArrayList<String> volunteerfeatures = new ArrayList<>();
    private FacetypeServiceImpl facetypeService;
    FacetypeEntity facetypeEntity;
//    @Resource
//    VolunteerService volunteerService;
//    ElderService elderService;

    /**
     请求服务类型 1：interaction 2：emotion 3：fall 4: banarea 5：faceType
     **/

    public VideoProcessingThread2(String userId,int type,List<VolunteerEntity> volunteers,List<ElderEntity> elders) {
        this.userId = userId;
        this.type = type;
        this.elders = elders;
        this.volunteers = volunteers;
        for(ElderEntity elder : elders)
        {
         this.old.add(elder.getName());
         this.oldfeatures.add(elder.getVector());
        }

        for(VolunteerEntity volunteer1 : volunteers)
        {
            this.volunteer.add(volunteer1.getName());
            this.volunteerfeatures.add(volunteer1.getVector());
        }

    }

    @Override
    public void run() {

        switch (type) {
            case 5:
                type2 = "faceType";
                break;
            case 6:
                type2 = "faceFeature";
                break; //只返回一帧图像
        }
        initInstance = new InitInstance();
        py = new Python();
        if (initInstance.openVideo()) {
            while (true) {
                Mat img = initInstance.getMatfromVideo();
                String tem = initInstance.matToBase64(img);
                //String path = savepath +userId+"\\"+savePicture.getTime();
                String path = "D:\\frame\\tmp.jpg";
                File file = new File(path);
                Imgcodecs.imwrite(path, img);
                System.out.println("file create");
                Map res = py.pingPython(old,oldfeatures,volunteer,volunteerfeatures,path , "http://127.0.0.1:5000/" + type2);
                String ak47 = initInstance.matToBase64(Imgcodecs.imread((String) res.get("result")));
                SseEmitterServer.sendMessage(userId, ak47);
                // 获取当前时间的时间戳
                Instant instant = Instant.now();
                // 创建DateTimeFormatter对象，定义时间戳格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 格式化时间戳为字符串
                String timestampString = formatter.format(instant);
                String types = (String) res.get("types");
                String  name = (String) res.get("name");
                facetypeEntity.setTime(timestampString);
                facetypeEntity.setName(name);
                facetypeEntity.setTypes(types);
                facetypeService.saveOrUpdate(facetypeEntity);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
