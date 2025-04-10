package com.library.management.controller;

import com.library.management.model.User;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ModelAndView showDashboard() {
        ModelAndView mav = new ModelAndView("admin-dashboard");
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-users";
    }

    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable("id") Long userId, @RequestParam("role") String role) {
        userService.updateUserRole(userId, role);
        return "redirect:/admin/dashboard";
    }
}