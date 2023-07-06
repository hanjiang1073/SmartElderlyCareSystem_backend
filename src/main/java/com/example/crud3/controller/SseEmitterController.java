package com.example.crud3.controller;

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

import static com.example.crud3.utils.Global.savepath;


@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    @Autowired
    MonggoDB monggoDB;
    InitInstance initInstance;

    Thread t;

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
        t.isInterrupted();

    }
        @GetMapping("/connect/{userId}/{Id}")
    public SseEmitter connect(@PathVariable String userId,@PathVariable int Id) {
        SseEmitter  s = SseEmitterServer.connect(userId);
        System.out.println("---------------"+Id+"----------------------");
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                initInstance = new InitInstance();
                if(initInstance.openVideo()){
                    while (true){
                        Mat img = initInstance.getMatfromVideo();
                        String tem = initInstance.matToBase64(img);
                        String path = savepath+savePicture.getTime();
                        File file = new File(path);
                        if(!file.exists()){
                            Imgcodecs.imwrite(path,img);
                            monggoDB.uploadFile(Id,path);
                        }
                        SseEmitterServer.sendMessage(userId,tem);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();
        return s;
    }
}