package com.example.crud3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crud3.entity.ElderEntity;
import com.example.crud3.entity.VolunteerEntity;
import com.example.crud3.mapper.ElderMapper;
import com.example.crud3.mapper.VolunteerMapper;
import com.example.crud3.py.Python;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.util.List;
import java.util.Map;

public class VideoProcessingThread32 extends  Thread {
    private InitInstance initInstance;
    //private Python py;
    private String userId;
    private int type;
    private String type2;
    private ElderMapper elderMapper;
    private VolunteerMapper volunteerMapper;


    public VideoProcessingThread32(String userId,int type) {
        this.userId = userId;
        this.type = type;
    }

    @Override
    public void run() {


        initInstance = new InitInstance();
        //py = new Python();
        if (initInstance.openVideo()) {
            while (true) {
                Mat img = initInstance.getMatfromVideo();
                String tem = initInstance.matToBase64(img);
                //String path = savepath +userId+"\\"+savePicture.getTime();
                String path = "D:\\frame\\";
                File file = new File(path);
                if (!file.exists()) {
                    Imgcodecs.imwrite(path, img);
                }
                //Map res = py.pingPython(elder,volunteer,path + "tmp.jpg", "http://127.0.0.1:5000/" + type2);
                //String ak47 = initInstance.matToBase64(Imgcodecs.imread((String) res.get("result")));
                SseEmitterServer.sendMessage(userId, tem);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

