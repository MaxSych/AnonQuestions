package askme.security;

import askme.service.CustomUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
public class Autorisation {

    private final SuccessHandler successHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public Autorisation(SuccessHandler successHandler, CustomUserDetailsService customUserDetailsService) {
        this.successHandler = successHandler;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(customUserDetailsService)
                .formLogin(form ->form
                        .loginPage("/main")
                        .loginProcessingUrl("/user_login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureUrl("/main?error=true")
                        .permitAll()
                )

                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/main?logout=true").invalidateHttpSession(true).clearAuthentication(true))

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()


                        .requestMatchers("/", "/register","/user_login", "/main","/profile/**", "/add").permitAll()

                        .anyRequest().authenticated()
                );



        return http.build();
    }
}