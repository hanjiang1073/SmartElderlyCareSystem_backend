package com.example.crud3.utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.util.Arrays;

import static com.example.crud3.utils.Global.*;
import static com.example.crud3.utils.Global.facexmlAbsPath;


public class facecheck {
    private  boolean  flag=false;
    private  Mat[] hists;
    private static int facenumber=3;
    //参数定义
    private static MatOfFloat ranges = new MatOfFloat(0f, 256f);
    private static MatOfInt histSize = new MatOfInt(10000000);
    public void setFacenumber(int num){
        this.facenumber=num;
    }
    public void initFaceCheck(int num,String facePath){

        setFacenumber(num);
        hists=new Mat[num];
        for(int i=0;i<facenumber;i++){
            Mat temp=conv_Mat(facePath +i+".jpg");
            hists[i]=new Mat();
            Imgproc.calcHist(Arrays.asList(temp), new MatOfInt(0), new Mat(), hists[i], histSize, ranges);
        }
    }
    public void initfs(){
        File f = new File(adminface);
        File[]  fs = f.listFiles();
        int i=0;
        setFacenumber(fs.length);
        hists=new Mat[facenumber];
        for(File ff : fs){
            Mat temp=conv_Mat(ff.getPath());
            hists[i]=new Mat();
            Imgproc.calcHist(Arrays.asList(temp), new MatOfInt(0), new Mat(), hists[i], histSize, ranges);
            i++;
        }
    }
    public  boolean faceCheck(Mat imgcompare){
        for(int i=0;i<facenumber;i++)
        {
            double res=compare_image(imgcompare,hists[i]);
            if(res>0.5){
                flag=true;
                return flag;
            }
//            System.out.println(res);
        }

        return false;
    }

    public static Mat conv_Mat(String img) {
        //读取图片Mat
        Mat imgInfo = Imgcodecs.imread(img);
        //此处调用了实体方法，实现灰度转化
        CvtMatEntity cvtMatEntity = CvtMatEntity.cvtR2G(imgInfo);
        //创建Mat矩形
        MatOfRect faceMat = new MatOfRect();
        //识别人人脸
        CascadeClassifier faceDetector = new CascadeClassifier(facexmlAbsPath);
        faceDetector.detectMultiScale(cvtMatEntity.gray, faceMat);
        for (Rect rect : faceMat.toArray()) {
            //选出灰度人脸
            Mat face = new Mat(cvtMatEntity.gray, rect);
            return face;
        }
        return null;
    }

    public static double compare_image(Mat mat, Mat hist) {
        //获得灰度人脸
//      Mat mat_1 = conv_Mat(img_1);
//      Mat mat_2 = conv_Mat(img_2);
//      Mat hist_1 = new Mat();
        Mat temp = new Mat();

        //实现图片计算
        Imgproc.calcHist(Arrays.asList(mat), new MatOfInt(0), new Mat(), temp, histSize, ranges);
//      Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);
        // 相关系数，获得相似度
        double res = Imgproc.compareHist(temp, hist, Imgproc.CV_COMP_CORREL);
        //返回相似度
        return res;
    }
}
