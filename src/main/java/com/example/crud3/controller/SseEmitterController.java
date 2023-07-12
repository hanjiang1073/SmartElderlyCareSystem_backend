package com.example.crud3.controller;

import com.example.crud3.py.Python;
import com.example.crud3.service.MonggoDB;
import com.example.crud3.service.impl.VideoProcessingThread;
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
        thread.interrupt();

    }
    /*视频监控模块i*/
        @GetMapping("/connect/{userId}/{type}")
    public SseEmitter connect(@PathVariable String userId,@PathVariable int type) {
        SseEmitter s = SseEmitterServer.connect(userId);
        System.out.println("---------------"+type+"----------------------");
         thread = new VideoProcessingThread(userId,type);
        thread.start();
        return s;
    }
}