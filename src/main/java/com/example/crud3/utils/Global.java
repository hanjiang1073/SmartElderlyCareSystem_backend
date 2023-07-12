package com.example.crud3.utils;

public class Global {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int CODEERROR = 5;  // 验证码错误
    public static final int right = 100;   // 普通用户，只能修改自己，查询所有用户
    public static final int right01 = 101; // 对应的管理员只能查询，修改别的用户信息
    public static final int right02 = 102; // 可以修改，查询，增加，删除用户
    public static final int right03 = 103; // root级用户，可以给别的管理员赋予权限
    public static final String SUCCES = "Operation succeed";
    public static final String FAILED = "Operation failed";
    public static final String TOKEN_CHECK= "NONE";
    public static final int REGISTER = 1; //注册
    public static final int FIND = 0; //找回
    public static final String adminface="D:\\2023\\summer\\adminface";
    public static final String savepath="D:\\2023\\summer\\savepath\\";


    //此路径不能有中文
    public static final String facexmlAbsPath="D:\\2023\\summer\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
    public static final String dllAbsPath="D:\\2023\\summer\\monitor\\src\\main\\resources\\lib.opencv\\opencv_java460.dll";
    public static final String ffmpegxmlAbsPath="D:\\2023\\summer\\opencv\\build\\bin\\opencv_videoio_ffmpeg460_64.dll";
}
