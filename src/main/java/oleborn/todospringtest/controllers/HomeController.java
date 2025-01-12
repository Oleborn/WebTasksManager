package oleborn.todospringtest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "tasks/login"; // Возвращает шаблон login.html
    }

    @GetMapping("/register/form")
    public String registerForm() {
        return "tasks/register"; // Возвращает шаблон register.html
    }
}