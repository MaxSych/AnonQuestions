package anonquestions.web;

import anonquestions.User;
import anonquestions.data.UserRepository;
import anonquestions.dto.request.LoginRequest;
import anonquestions.dto.request.RegistrationRequest;
import anonquestions.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegisterController {

    private PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserRepository userRepository;

    public RegisterController(AuthService authService,  UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/register")
    public String GetLogin() {

        return "register";
    }

    @GetMapping("/main")
    public String GetMain(){

        return "main";
    }



    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest request) {
        authService.register(request);
        return "redirect:/main";
}
}
