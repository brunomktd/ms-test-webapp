package com.example.webapp.controller;

import com.example.webapp.service.LoginService;
import com.example.webapp.vo.LoginVO;
import com.example.webapp.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public String login(){
        return "login/login";
    }

    @PostMapping
    public String logar(LoginVO login, HttpSession session){
        TokenVO token = loginService.auth(login);
        session.setAttribute("token", token);
        return "redirect:/conta";
    }
}
