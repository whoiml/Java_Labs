package com.labs.ad_board.http.controller;

import com.labs.ad_board.dto.UserCreateEditDto;
import com.labs.ad_board.dto.UserReadDto;
import com.labs.ad_board.service.ReCaptchaValidationService;
import com.labs.ad_board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ReCaptchaValidationService validator;

    @GetMapping("/register")
    public String getRegisterPage() {
        log.info("trying to register");
        return "register";
    }

    @PostMapping("/register")
    public String registerHandler(
            @ModelAttribute("user")
            UserCreateEditDto user,
            @RequestParam(name="g-recaptcha-response")
            String captcha,
            Model model
    ) {
        log.info(user.toString());
        log.info("Captcha: " + captcha);

        List<String> errors = new ArrayList<>();

        if(!validator.validateCaptcha(captcha)) {
            log.info("Captcha verification failed!");
            errors.add("Please Verify Captcha");
            model.addAttribute("errors", errors);
            return "register";
        }
        if (userService.usernameExist(user.getUsername())) {
            errors.add("Username exist!");
        }
        if (userService.emailExist(user.getEmail())) {
            errors.add("Email exist!");
        }
        if (!user.getPassword().equals(user.getRePassword())) {
            errors.add("Passwords should match!");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "register";
        }

        UserReadDto newUser = userService.registerNewUserAccount(user);

        model.addAttribute("user", newUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(
            @RequestParam(value = "error", required = false)
            String error,
            Model model
    ) {
        log.info("Returning login page");
        if (error != null) {
            model.addAttribute(
                    "error",
                    "Incorrect email or username"
            );
        }
        return "login";
    }
}
