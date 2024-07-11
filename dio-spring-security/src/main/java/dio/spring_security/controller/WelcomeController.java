package dio.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping()
    public String welcome() {
        return "Welcome to My Spring Boot Web API";
    }

    @GetMapping("/users")
    public String userInfo() {
        return "Informação acessível apenas a usuários com a role USER";
    }

    @GetMapping("/managers")
    public String adminInfo() {
        return "Informação acessível apenas a usuários com a role MANAGER";
    }
}