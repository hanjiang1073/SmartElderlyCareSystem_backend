package com.example.crud3.controller;

import com.example.crud3.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MailController
 * @Description: //TODO 邮件
 * @Author wyq
 * @Date 2022/4/18 21:47
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/email")
public class MailController {
    @Autowired
    private MailService mailService;

//    //发送邮箱验证码
//    @GetMapping("send/{email}")
//    public int sendEmail(@PathVariable String email) {
//        //key 邮箱号  value 验证码
//        String code = redisTemplate.opsForValue().get(email);
//        //从redis获取验证码，如果获取获取到，返回ok
//        if (!StringUtils.isEmpty(code)) {
//            return 1;
//        }
//        //如果从redis获取不到，生成新的6位验证码
//        code = RandomUtil.getSixBitRandom();
//        //调用service方法，通过邮箱服务进行发送
//        boolean isSend = mailService.sendMail(email, code);
//        //生成验证码放到redis里面，设置有效时间为5分钟
//        if (isSend) {
//            redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
//            return 1;
//        } else {
//            return 2;
//        }
//    }

    @GetMapping("/register")
    @ResponseBody
    public int sendCode(@RequestParam("email")String email,@RequestParam("type")int type){

        return mailService.sendCode(email, type);
    }

    @GetMapping("findcode/{email}/{type}")
    @ResponseBody
    public String findCode(@PathVariable String email,@PathVariable int type){
        return mailService.getCode(email, type);
    }
}
