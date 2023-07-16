package com.example.crud3.service.impl;

import com.example.crud3.entity.BanareaEntity;
import com.example.crud3.entity.EmotionEntity;
import com.example.crud3.entity.FacetypeEntity;
import com.example.crud3.entity.InteractionEntity;
import com.example.crud3.mapper.BanareaMapper;
import com.example.crud3.py.Python;
import com.example.crud3.utils.InitInstance;
import com.example.crud3.utils.SseEmitterServer;
import com.example.crud3.utils.savePicture;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import java.time.*;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import static com.example.crud3.utils.Global.savepath;

public class VideoProcessingThread extends  Thread {
    private InitInstance initInstance;
    private Python py;
    private String userId;
    private int type;
    private String type2;
    private BanareaServiceImpl banareaService;
    private EmotionServiceImpl emotionService;
    private  FacetypeServiceImpl facetypeService;
    private InteractionServiceImpl interactionService;
    private InteractionEntity interactionEntity;
    private FacetypeEntity facetypeEntity;
    private EmotionEntity emotionEntity;
    private BanareaEntity banareaEntity;

 /**
 请求服务类型 1：interaction 2：emotion 3：fall 4: banarea 5：faceType
 **/

    public VideoProcessingThread(String userId,int type) {
        this.userId = userId;
        this.type = type;
    }

    @Override
    public void run( ) {
        switch (type)
        {
            case 1: type2 = "interaction" ;break;
            case 2: type2 = "emotion";  break;
            case 3: type2 = "fall"; break;
            case 4: type2 = "banarea"; break;
            case 5: type2 = "faceType"; break;
            case 6: type2 = "faceFeature"; break; //只返回一帧图像
        }
        initInstance = new InitInstance();
        py = new Python();
        if (initInstance.openVideo()) {
            while (true) {
                Mat img = initInstance.getMatfromVideo();
                String tem = initInstance.matToBase64(img);
                //String path = savepath +userId+"\\"+savePicture.getTime();
                String path = "D:\\frame\\";
                File file = new File(path);
                    Imgcodecs.imwrite(path + "tmp.jpg", img);
                    Map res = py.pingPython(path + "tmp.jpg", "http://127.0.0.1:5000/" + type2);
                    String ak47 = initInstance.matToBase64(Imgcodecs.imread((String) res.get("result")));
                    SseEmitterServer.sendMessage(userId, ak47);
                    ArrayList<String> message = (ArrayList) res.get("msg");

                    if(!message.isEmpty())
                    {
                        System.out.println(message);
                        if(type == 1) {
                            for (String m : message)
                            {
                                // 获取当前时间的时间戳
                                Instant instant = Instant.now();
                                // 创建DateTimeFormatter对象，定义时间戳格式
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                // 格式化时间戳为字符串
                                String timestampString = formatter.format(instant);
                                interactionEntity.setTime(timestampString);
                                interactionEntity.setMessage(m);
                                interactionService.save(interactionEntity);
                        }
                        }
                        else if(type == 2)
                        {
                            for (String m : message)
                            {
                                // 获取当前时间的时间戳
                                Instant instant = Instant.now();
                                // 创建DateTimeFormatter对象，定义时间戳格式
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                // 格式化时间戳为字符串
                                String timestampString = formatter.format(instant);
                                emotionEntity.setTime(timestampString);
                                emotionEntity.setEmotion(m);
                                emotionService.save(emotionEntity);

                            }
                        }
                        else if (type == 3)
                        {
                            // 获取当前时间的时间戳
                            Instant instant = Instant.now();
                            // 创建DateTimeFormatter对象，定义时间戳格式
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            // 格式化时间戳为字符串
                            String timestampString = formatter.format(instant);



                        }
                        else if (type == 4)
                        {
                            for (String m : message)
                            {
                                // 获取当前时间的时间戳
                                Instant instant = Instant.now();
                                // 创建DateTimeFormatter对象，定义时间戳格式
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                // 格式化时间戳为字符串
                                String timestampString = formatter.format(instant);
                                banareaEntity.setTime(timestampString);
                                banareaEntity.setMessage(m);
                                banareaService.save(banareaEntity);
                            }
                        }
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}