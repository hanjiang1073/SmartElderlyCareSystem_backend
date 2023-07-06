package com.example.crud3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SkipController {

    @GetMapping("/toLog")
    public String toLog(){
        return "html/Login";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "html/Register";
    }

    @GetMapping("/toWs/{userId}")
    public String toWs(@PathVariable("userId")Integer userId, HttpSession session){
        session.setAttribute("Id",userId);
//        session.getAttribute("nowUser");
        return "html/ws";
    }


    @GetMapping("/toRecord")
    public String toRecord(){
        return "html/record";
    }

    @GetMapping("/toReplay")
    public String toReplay(){
        return "html/replay";
    }

    @GetMapping("/toFace")
    public String toFace(){
        return "html/admin/face";
    }

    @GetMapping("/toAdmin")
    public String toHome_admin(HttpServletRequest request, HttpServletResponse response){

//        try {
//            response.sendRedirect("html/admin/home_admin");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("sdfwefsdfs");
        return "html/admin/home_admin";
    }

    @GetMapping("/toMonitor_admin")
    public String toMonitor_admin(){
        return "html/admin/monitor_admin";
    }
    @GetMapping("/toRecord_admin")
    public String toRecord_admin(){
        return "html/admin/record_admin";
    }

    @GetMapping("/toReplay_admin")
    public String toReplay_admin(){
        return "html/admin/replay_admin";
    }

    @GetMapping("/to_User_info_jurisdiction")
    public String toUser_info_jurisdiction(){
        return "html/admin/user_info_jurisdiction";
    }

    @GetMapping("/toStatic_admin")
    public String toStatic_admin(){
        return "html/admin/static_admin";
    }



    @GetMapping("/toWhitelist")
    public String toWhitelist(){
        return "html/admin/whitelist";
    }
    @GetMapping("/toRecord_mem")
    public String toRecord_mem(){
        return "html/member/record_mem";
    }
    @GetMapping("/toStatistics_mem")
    public String toStatistics_mem(){
        return "html/member/static_mem";
    }
    @GetMapping("/toReplay_mem")
    public String toReplay_mem(){
        return "html/member/replay_mem";
    }
    @GetMapping("/toEdit")
    public String toEdit(){
        return "html/member/edit";
    }
    @GetMapping("/toResetpwd")
    public String toResetpwd(){
        return "html/member/resetpwd";
    }
    @GetMapping("/toStatic_mem")
    public String toStatic_men(){
        return "html/member/static_mem";
    }
    @GetMapping("/toHome_mem")
    public String toHome_mem(){
        return "html/member/home_mem";
    }
    @GetMapping("/toCancellation")
    public String toCancellation(){
        return "html/member/cancellation";
    }
}
