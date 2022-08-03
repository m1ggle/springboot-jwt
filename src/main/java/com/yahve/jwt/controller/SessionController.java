package com.yahve.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@RestController
public class SessionController {

    @GetMapping("/test")
    public String sessionTest(String userName, HttpServletRequest request ){
        request.getSession().setAttribute("session",userName);
        return "login ok~";

    }
}
