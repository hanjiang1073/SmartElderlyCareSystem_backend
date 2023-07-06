package com.example.crud3.utils;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class  CvtMatEntity{
    //原图Mat
    public Mat img;
    //灰度图Mat
    public Mat gray;
    public static CvtMatEntity cvtR2G(Mat img){
        CvtMatEntity cvtMatEntity = new CvtMatEntity();
        Mat rgb = new Mat();
        //实现图片灰度转换
        Imgproc.cvtColor(img, rgb, Imgproc.COLOR_BGR2RGB);
        Mat gray = new Mat();
        Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
        //赋值
        cvtMatEntity.img = img;
        cvtMatEntity.gray = gray;
        //返回
        return cvtMatEntity;
    }
}
