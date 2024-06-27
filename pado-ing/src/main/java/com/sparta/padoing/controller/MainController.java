//package com.sparta.padoing.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class MainController {
//
//    @GetMapping("/")
//    @ResponseBody
//    public String mainAPI() {
//        return "main route";
//    }
//
//    @GetMapping("/loginTest")
//    @ResponseBody
//    public String loginAPI() {
//        return "login route";
//    }
//}

package com.sparta.padoing.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String mainAPI() {
        return "main route";
    }

    @GetMapping("/loginTest")
    @ResponseBody
    public String loginAPI() {
        return "login route";
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logoutAPI(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                if (cookie.getName().equals("__Host-GAPS")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return "logout successful";
    }
}