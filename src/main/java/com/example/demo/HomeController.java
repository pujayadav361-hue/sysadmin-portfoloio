package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("name", "Pooja Yadav");
        model.addAttribute("role", "Senior Linux & AWS Cloud Administrator");

        return "index";
    }

    @GetMapping("/status")
    public String status() {
        return "status";
    }
}
