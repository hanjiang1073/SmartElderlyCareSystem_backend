package com.example.crud3.service.impl;

import com.example.crud3.service.MailService;
import com.example.crud3.service.UserService;
import com.example.crud3.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MailServiceImpl
 * @Description: //TODO
 * @Author wyq
 * @Date 2022/4/18 21:50
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserService userService;
    @Value("${spring.mail.username}")
    private String UserName;//获得配置文件中的username

    @Autowired
    private JavaMailSender mailSender;//注入发送邮件的bean

    @Override
    public String getCode(String email, int type) {
        String key = email + "_";
        if (type == Global.REGISTER){
            key += "register_code";
        }else {
            key += "find_code";
        }
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null){
            return "";
        }
        return (String) result;
    }

    @Override
    public int sendCodeForFind(String username) {
        //先获取用户名对应的邮箱
        String email = userService.getUserByUserName(username).getEmail();
        long codeL = System.nanoTime();
        //生成6位验证码
        String codeStr = Long.toString(codeL);
        codeStr = codeStr.substring(codeStr.length() - 8, codeStr.length() - 2);
        //存入redis
        String key_code = username + "_find_code"; //kevin_find_code
        redisTemplate.opsForValue().set(key_code,codeStr,60*5, TimeUnit.SECONDS);//验证码有效时间是5分钟
        //发送到用户邮箱
        String content = "欢迎使用仓库入侵检测系统找回密码,您的验证码是:"+ codeStr +",有效时间5分钟";
        sendSimpleMail(email,"找回密码验证码",content);
        return Global.SUCCESS;
    }


    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom("1782532694@qq.com");
        //收件人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }
    @Override
    public int sendCodeREGISTER(String email){
        long codeL = System.nanoTime();
        //先生成6位验证码
        String codeStr = Long.toString(codeL);
        codeStr = codeStr.substring(codeStr.length() - 8, codeStr.length() - 2);
        //存入redis
        String key_code = email + "_register_code";
        String key_times = email + "_register_times";
        redisTemplate.opsForValue().set(key_code,codeStr,60*5,TimeUnit.SECONDS);//验证码有效时间是5分钟
//        redisTemplate.opsForValue().set(key_times,String.valueOf(times+1),1, TimeUnit.HOURS);//更新最近验证码发送时间
        //发送到用户邮箱
        String content = "欢迎使用智慧养老系统注册用户,验证码是:"+ codeStr +",有效时间5分钟";
        sendSimpleMail(email,"注册验证码",content);
        return Global.SUCCESS;
    }
    @Override
    public int sendCode( String email,int type) {
        if (type == Global.REGISTER){
            return sendCodeREGISTER(email);
        }else if (type == Global.FIND){
            return sendCodeForFind(email);
        }
        return Global.FAIL;
    }
}
