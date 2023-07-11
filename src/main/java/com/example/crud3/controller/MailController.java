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
