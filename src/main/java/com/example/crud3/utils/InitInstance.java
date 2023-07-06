package com.example.crud3.utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import static com.example.crud3.utils.Global.*;
import static org.opencv.imgproc.Imgproc.INTER_LINEAR;

public class InitInstance {

    private static Logger logger = LoggerFactory.getLogger(InitInstance.class);
    //脸部识别实例
    private static CascadeClassifier faceDetector;
    //打开摄像头
    VideoCapture videoCapture;

    facecheck  face;
    Base64.Encoder encoder;
    //此类加载人脸识别模块
    public static void init(String dllAbsPath, String facexmlAbsPath, String eyexmlAbsPath) {
        logger.info("开始读取脸部识别实例");
        //加载dll文件
        System.load(dllAbsPath);
        faceDetector = new CascadeClassifier(facexmlAbsPath);
        if (faceDetector.empty()) {
            logger.error("人脸识别模块读取失败");
        } else logger.info("人脸识别模块读取成功");
    }
    public  InitInstance() {
        logger.info("开始读取脸部识别实例");
        //加载dll文件
        System.load(dllAbsPath);
        System.load(ffmpegxmlAbsPath);
       // faceDetector = new CascadeClassifier(facexmlAbsPath);
       // face=new facecheck();
        //face.initFaceCheck(1,adminface);
       // face.initfs();
        encoder = Base64.getEncoder();
      //  if (faceDetector.empty()) {
     //       logger.error("人脸识别模块读取失败");
      //  } else logger.info("人脸识别模块读取成功");
    }

    public boolean openVideo(){
        //打开摄像头
//        String p = "D:\\java\\opencv\\build\\bin\\opencv_videoio_ffmpeg460_64.dll";
//        System.load(p);
//        String url = "rtmp://8.130.83.55:1935/mylive/1234";
//        videoCapture = new VideoCapture(url,CAP_FFMPEG);
        videoCapture = new VideoCapture(0);
        // 获取可用摄像头设备的数量
        // 输出可用摄像头设备的索引号

        //判断摄像头是否打开
        if (!videoCapture.isOpened()) {
            logger.error("相机打开失败");
            return false;
        }
        return true;
    }
    public void closeVideo(){
        videoCapture.release();
    }
    public Mat getMatfromVideo(){
        Mat img = new Mat();
        //读取摄像头下的图像
        if (!videoCapture.read(img)) return null;
        return img;
    }
    public String matToBase64(Mat img){
            if(img==null){
                return null;
            }
            //图像缩放比例
            double scale =4.0;
            double fx=1/scale;

            //初始化白名单人脸库，第一个参数为图片数量，第二个参数为图片存放地址
            Mat rgb = new Mat();
            Imgproc.cvtColor(img, rgb, Imgproc.COLOR_BGR2RGB);
            Mat gray = new Mat();
            Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);

            //图片缩放，减少计算量
            Mat smallImg=new Mat();
            Imgproc.resize(gray,smallImg,smallImg.size(),fx,fx,INTER_LINEAR);
            //释放无用mat
            gray.release();

            //图片直方图均衡化
            Imgproc.equalizeHist(smallImg,smallImg);

            MatOfRect faveRect = new MatOfRect();
            //检测人脸
          //  faceDetector.detectMultiScale(smallImg, faveRect);
            //图形面勾选人脸
            for (Rect re : faveRect.toArray()) {
                double lx=re.x*scale;
                double ly=re.y*scale;
                double lwidth=(re.x + re.width)*scale;
                double lheigeht=(re.y + re.height)*scale;
                //框出人脸
                Imgproc.rectangle(img, new Point(lx, ly), new Point(lwidth, lheigeht), new Scalar(0, 255, 0), 2);
                //创建人脸识别出的矩形变量
                Mat imgcompare = new Mat(smallImg, new Rect(re.x, re.y, re.width, re.height));
                //在白名单库中对比
                boolean flag = face.faceCheck(imgcompare);
                if (flag)
                    Imgproc.putText(img, "admin", new Point(lx, ly), 2, 1, new Scalar(0, 255, 0));
                else {
                    Imgproc.rectangle(img, new Point(lx, ly), new Point(lwidth, lheigeht), new Scalar(0, 0, 255), 2);
                    Imgproc.putText(img, "warn", new Point(lx, ly), 2, 1, new Scalar(0, 0, 255));
//                    savePicture.savePicture(img);
                }
            }
            String jpg_base64 = null;
            Imgcodecs.imwrite(savepath + "tmp.jpg", img);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(mat2BI(img), "jpg", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = baos.toByteArray();
            jpg_base64 = encoder.encodeToString(Objects.requireNonNull(bytes));
            return jpg_base64;
        }

    public static BufferedImage mat2BI(Mat mat) {
        int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0, 0, data);
        int type = mat.channels() == 1 ?
                BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;

        if (type == BufferedImage.TYPE_3BYTE_BGR) {
            for (int i = 0; i < dataSize; i += 3) {
                byte blue = data[i + 0];
                data[i + 0] = data[i + 2];
                data[i + 2] = blue;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);

        return image;
    }
}