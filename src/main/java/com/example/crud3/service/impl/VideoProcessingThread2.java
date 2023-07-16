package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crud3.entity.ElderEntity;
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

import javax.annotation.Resource;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.util.List;
import java.util.Map;

public class VideoProcessingThread2 extends  Thread {
    private InitInstance initInstance;
    private Python py;
    private String userId;
    private int type;
    private String type2;

    private ElderMapper elderMapper;
    private VolunteerMapper volunteerMapper;
//    @Resource
//    VolunteerService volunteerService;
//    ElderService elderService;

    /**
     请求服务类型 1：interaction 2：emotion 3：fall 4: banarea 5：faceType
     **/
    public VideoProcessingThread2(String userId,int type) {
        this.userId = userId;
        this.type = type;
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
        List<ElderEntity> elder = elderMapper.selectList(null);
               //// elderMapper.selectList(null);
        List<VolunteerEntity> volunteer =  volunteerMapper.selectList(null);
        initInstance = new InitInstance();
        py = new Python();
        if (initInstance.openVideo()) {
            while (true) {
                Mat img = initInstance.getMatfromVideo();
                String tem = initInstance.matToBase64(img);
                //String path = savepath +userId+"\\"+savePicture.getTime();
                String path = "D:\\frame\\";
                File file = new File(path);

                    Imgcodecs.imwrite(path, img);
                    System.out.println("file create");

                Map res = py.pingPython(elder,volunteer,path + "tmp.jpg", "http://127.0.0.1:5000/" + type2);
                String ak47 = initInstance.matToBase64(Imgcodecs.imread((String) res.get("result")));
                SseEmitterServer.sendMessage(userId, ak47);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
