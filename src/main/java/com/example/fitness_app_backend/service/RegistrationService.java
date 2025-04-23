package com.example.fitness_app_backend.service;

import com.example.fitness_app_backend.dto.RegistrationRequestDTO;
import com.example.fitness_app_backend.enums.UserRole;
import com.example.fitness_app_backend.model.Token;
import com.example.fitness_app_backend.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService{
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequestDTO request){
        User user = new User(request.getFirstname(), request.getLastname(),
                request.getEmail(), request.getPassword(), UserRole.USER);

        String token = userService.signUpUser(user);
        String confirmationLink = "http://localhost:9090/registration/confirmation?token=" + token;
        //send email
        emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), confirmationLink));
        return token;
    }

    @Transactional
    public String confirmToken(String token){
        String loginLink = "http://localhost:9090/registration/login";
        Token confirmationToken = tokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found."));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException(buildConfirmedPage(
                    loginLink,"Email already confirmed.")
            );
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token expired");
        }

        tokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());

        return buildConfirmedPage(loginLink, "Email confirmed");
    }

    private String buildEmail(String name, String link) {
        return
                "<p> Hi " + name +
                        ",</p>" +
                        "<p> Thank you for registering. Please click on the below link to activate your account: </p>" +
                        "<blockquote><p> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 10 minutes.";
    }
    private String buildConfirmedPage(String link, String message){
        return "<p> " + message + ". Please click on the below link to Login: </p>" +
                "<blockquote><p> <a href=\"" + link + "\">Login</a> </p>";
    }
}

