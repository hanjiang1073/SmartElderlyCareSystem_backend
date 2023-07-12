package com.example.crud3.service.impl;

import com.example.crud3.py.Python;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import com.example.crud3.utils.savePicture;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.Map;

import static com.example.crud3.utils.Global.savepath;

public class VideoProcessingThread extends  Thread {
    private InitInstance initInstance;
    private Python py;
    private String userId;
    private int type;
    private String type2;

 /**
 请求服务类型 1：interaction 2：emotion 3：fall 4: banerea 5：faceType
 **/
    public VideoProcessingThread(String userId,int type) {
        this.userId = userId;
        this.type = type;
    }

    @Override
    public void run() {
        initInstance = new InitInstance();
        py = new Python();
        if (initInstance.openVideo()) {
            while (true) {
                Mat img = initInstance.getMatfromVideo();

                String tem = initInstance.matToBase64(img);
                String path = savepath +userId+"\\"+savePicture.getTime();
                File file = new File(path);
                if (!file.exists()) {
                    Imgcodecs.imwrite(path, img);
                }
                switch (type)
                {
                    case 1: type2 = "interaction" ;break;
                    case 2: type2 = "emotion";  break;
                    case 3: type2 = "fall"; break;
                    case 4: type2 = "banerea"; break;
                    case 5: type2 = "faceType"; break;


                }
                Map res = py.pingPython(path+"tmp.jpg", "http://127.0.0.1:5000/"+type2);
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