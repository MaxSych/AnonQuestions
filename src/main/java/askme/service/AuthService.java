package askme.service;

import askme.User;
import askme.data.UserRepository;
import askme.dto.request.RegistrationRequest;
import askme.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public void register(RegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmpassword())) {
            log.error("Registration failed: passwords do not match for email {}", request.getEmail());
            throw new IllegalArgumentException("Passwords do not match");
        }


        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Attempt to register already existing email: {}", request.getEmail());
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }


        log.info("Starting registration process for email: {}", request.getEmail());
        User user = userMapper.toEntity(request);


        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("ROLE_USER");

        userRepository.saveAndFlush(user);
        log.info("Successfully registered user with ID: {}", user.getId());
    }
}