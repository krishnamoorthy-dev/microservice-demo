package com.krishna.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
    @GetMapping("/public/info")
    public String getPublicInfo() {
        return "Public content: No login required!";
    }

    @GetMapping("/user/profile")
    public String getUserProfile() {
        return "User content: Hello authenticated user!";
    }

    @GetMapping("/admin/dashboard")
    public String getAdminDashboard() {
        return "Admin content: Secret dashboard restricted to admins.";
    }
}
