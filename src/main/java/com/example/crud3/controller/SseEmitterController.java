package com.example.crud3.controller;

import com.example.crud3.entity.Connect3RequestEntity;
import com.example.crud3.entity.ElderEntity;
import com.example.crud3.entity.VolunteerEntity;
import com.example.crud3.py.Python;
import com.example.crud3.service.ElderService;
import com.example.crud3.service.MonggoDB;
import com.example.crud3.service.VolunteerService;
import com.example.crud3.service.impl.VideoProcessingThread;
import com.example.crud3.service.impl.VideoProcessingThread2;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import com.example.crud3.utils.savePicture;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
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

    @Resource
    VolunteerService volunteerService;
    ElderService elderService;

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
    @GetMapping("/connect2/{userId}/{type}")
    public SseEmitter connect2(@PathVariable String userId,@PathVariable int type) {
        SseEmitter s = SseEmitterServer.connect(userId);
        System.out.println("---------------"+type+"----------------------");
        thread = new VideoProcessingThread2(userId,type);
        thread.start();
        return s;
    }

    @PostMapping("/connect3")
    public SseEmitter connect3(@RequestBody Connect3RequestEntity connect3Request){
        SseEmitter s = SseEmitterServer.connect("53");
        initInstance = new InitInstance();
        Python py = new Python();
        String valorant = null;
        initInstance.openVideo();
        if (initInstance.openVideo()) {

                Mat img = initInstance.getMatfromVideo();
                String tem = initInstance.matToBase64(img);
                SseEmitterServer.sendMessage("53",tem);
                //String path = savepath +userId+"\\"+savePicture.getTime();
                String path = "D:\\frame\\";
                File file = new File(path);
                if (!file.exists()) {
                    Imgcodecs.imwrite(path, img);
                }
                Map res = py.pingPython(path + "tmp.jpg", "http://127.0.0.1:5000/faceFeature");
                //String valorant = initInstance.matToBase64(Imgcodecs.imread((String) res.get("result")));
                valorant = (String) res.get("result");


            }

            if(connect3Request.isType()){
                ElderEntity elder = new ElderEntity();
                elder.setName(connect3Request.getName());
                elder.setAge(connect3Request.getAge());
                elder.setPhone(connect3Request.getPhone());
                elder.setDescription(connect3Request.getDesc());
                elder.setVector(valorant);
                elderService.save(elder);
            }else if(!connect3Request.isType()){
                VolunteerEntity volunteer = new VolunteerEntity();
                volunteer.setName(connect3Request.getName());
                volunteer.setAge(connect3Request.getAge());
                volunteer.setPhone(connect3Request.getPhone());
                volunteer.setDescription(connect3Request.getDesc());
                volunteer.setVector(valorant);
                volunteerService.save(volunteer);
            }else {
                System.out.println("die");
            }
            return  s;
    }
}