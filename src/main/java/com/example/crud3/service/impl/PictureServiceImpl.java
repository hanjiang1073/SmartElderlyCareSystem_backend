package com.example.crud3.service.impl;

import com.example.crud3.service.PictureService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;

@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public void getPicture(HttpServletRequest request, HttpServletResponse response,String path) throws IOException{
        //使用字节流读取本地图片
        ServletOutputStream out=null;
        BufferedInputStream buf=null;
        //创建了一个文件对象,对应的填图片存放的路径(需要的请填写自己的)
//        File file = new File("C:\\Users\\FUBOFENG\\Desktop\\image.jpg");
        File file = new File(path);
        try {
            //使用输入读取缓冲流读取一个文件输入流
            buf = new BufferedInputStream(new FileInputStream(file));
            //利用respone获取一个字节流输出对象
            out = response.getOutputStream();
            //定义个数组,由于读取缓冲流的内容
            byte[] buffer = new byte[1024];
            //循环一直读取缓冲流中的内容到输出的对象中
            while (buf.read(buffer)!=-1){
                out.write(buffer);
            }
            //写出请求的地方
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭
            if (buf!=null) buf.close();
            if (out!=null) out.close();
        }


    }

    @Override
    public String toBase64(String filePath) {
        FileInputStream inputStream = null;
        String base64Str = "";
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            inputStream = new FileInputStream(filePath);
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            base64Str = encoder.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return base64Str;
    }
}
