package com.example.crud3.controller;

import com.example.crud3.py.Python;
import com.example.crud3.service.MonggoDB;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import com.example.crud3.utils.savePicture;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.util.Map;

import static com.example.crud3.utils.Global.savepath;


@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    @Autowired
    MonggoDB monggoDB;
    InitInstance initInstance;

    Thread thread;

    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        for (int i = 0; i < 10; i++) {
            SseEmitterServer.batchSendMessage(message + i);
        }
//        SseEmitterServer.batchSendMessage(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

    @GetMapping("/close/{userId}")
    public void close(@PathVariable String userId){
        SseEmitterServer.removeUser(userId);
        initInstance.closeVideo();
        thread.isInterrupted();

    }
        @GetMapping("/connect/{userId}/{Id}")
    public SseEmitter connect(@PathVariable String userId,@PathVariable int Id) {
        SseEmitter  s = SseEmitterServer.connect(userId);
        System.out.println("---------------"+Id+"----------------------");
         thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initInstance = new InitInstance();
                Python py = new Python();//test
                if(initInstance.openVideo()){

                    while (true){
                        Mat img = initInstance.getMatfromVideo();
                        //String a = initInstance.matToBase64(Imgcodecs.imread("D:\\frame\\a.png"));//test
                        String tem = initInstance.matToBase64(img);
                        String path = savepath+savePicture.getTime();
                        File file = new File(path);
                        if(!file.exists()){
                            Imgcodecs.imwrite(path,img);
                            monggoDB.uploadFile(Id,path);
                        }

                      Map res = py.pingPython("D:\\frame\\tmp.jpg","http://127.0.0.1:5000/interaction");//test
                       String ak47 = initInstance.matToBase64(Imgcodecs.imread((String) res.get("interactionresult")));
                        //String ak47 = initInstance.matToBase64(Imgcodecs.imread("C:\\Users\\Lenovo\\PycharmProjects\\computerVision\\picture\\banresult.jpg"));//test
                        SseEmitterServer.sendMessage(userId,ak47);

                        try {
                            Thread.sleep(50);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        thread.start();
        return s;
    }
}