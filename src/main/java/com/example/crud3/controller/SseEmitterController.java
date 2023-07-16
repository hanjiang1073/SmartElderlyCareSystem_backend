package com.example.crud3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crud3.entity.Connect3RequestEntity;
import com.example.crud3.entity.ElderEntity;
import com.example.crud3.entity.VolunteerEntity;
import com.example.crud3.mapper.ElderMapper;
import com.example.crud3.mapper.VolunteerMapper;
import com.example.crud3.payload.request.Connect4Request;
import com.example.crud3.py.Python;
import com.example.crud3.service.ElderService;
import com.example.crud3.service.MonggoDB;
import com.example.crud3.service.VolunteerService;
import com.example.crud3.service.impl.VideoProcessingThread;
import com.example.crud3.service.impl.VideoProcessingThread2;
import com.example.crud3.service.impl.VideoProcessingThread32;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;


@RestController
@RequestMapping("/sse")
@CrossOrigin
public class SseEmitterController {

    @Autowired
    MonggoDB monggoDB;
    InitInstance initInstance;

    Thread thread;
    int image = 0;
    @Resource
    VolunteerService volunteerService;
    ElderService elderService;
    @Autowired
    ElderMapper elderMapper;
    @Autowired
    VolunteerMapper volunteerMapper;
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
    @GetMapping("/connect32")
    public SseEmitter connect32(@PathVariable String userId,@PathVariable int type) {
        SseEmitter s = SseEmitterServer.connect(userId);
        System.out.println("---------------"+type+"----------------------");
        thread = new VideoProcessingThread32(userId,type);
        thread.start();
        return s;
    }

    @PostMapping("/connect3")
    public boolean connect3(@RequestBody Connect3RequestEntity connect3Request){

            if(connect3Request.isOld()){
                ElderEntity elder = new ElderEntity();
                elder.setName(connect3Request.getName());
                elder.setAge(connect3Request.getAge());
                elder.setPhone(connect3Request.getPhone());
                elder.setDescription(connect3Request.getDesc());
                elder.setVector("");
                elderService.save(elder);
            }else if(!connect3Request.isOld()){
                VolunteerEntity volunteer = new VolunteerEntity();
                volunteer.setName(connect3Request.getName());
                volunteer.setAge(connect3Request.getAge());
                volunteer.setPhone(connect3Request.getPhone());
                volunteer.setDescription(connect3Request.getDesc());
                volunteer.setVector("");
                volunteerService.save(volunteer);
            }else {
                System.out.println("failed");
                return false;
            }
            return  true;
    }
    @PostMapping("/connect4")
    public boolean connect4(@RequestBody Connect4Request connect4Request){
        initInstance = new InitInstance();
        Python py = new Python();
        String valorant;
        try {
            BufferedImage carrierImage = decodeBase64Image(connect4Request.getImg());
            // 保存embeddedImage到本地文件系统
            File outputImage = new File("D:\\frame\\"+image+".jpg");
            image++;
            ImageIO.write(carrierImage, "jpg", outputImage);
            // 获取保存的embeddedImage的URL
            URL imageUrl = outputImage.toURI().toURL();
            String embeddedImageUrl = imageUrl.toString();
            Map res = py.pingPython( embeddedImageUrl, "http://127.0.0.1:5000/faceFeature");
            //xianshi
            // String v = initInstance.matToBase64(Imgcodecs.imread("D:\\frame\\" + "tmp.jpg"));
            valorant = (String) res.get("result");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if(connect4Request.isOld()){
            QueryWrapper<ElderEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("elderid",Integer.parseInt(connect4Request.getId()));
            ElderEntity elder = elderMapper.selectOne(queryWrapper);
            elder.setVector(valorant);
            elderService.save(elder);
        }else if(!connect4Request.isOld()){
            QueryWrapper<VolunteerEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("volunteerid",Integer.parseInt(connect4Request.getId()));
            VolunteerEntity volunteer = volunteerMapper.selectOne(queryWrapper);
            volunteer.setVector(valorant);
            volunteerService.save(volunteer);
        }else {
            System.out.println("failed");
            return false;
        }
        return  true;
    }



    private BufferedImage decodeBase64Image(String base64Image) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);
        return ImageIO.read(new ByteArrayInputStream(imageBytes));

    }
}