package askme.web;

import askme.data.UserRepository;
import askme.dto.request.RegistrationRequest;
import askme.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
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
