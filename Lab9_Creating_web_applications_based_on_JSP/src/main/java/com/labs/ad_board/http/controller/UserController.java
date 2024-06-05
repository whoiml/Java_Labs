package com.labs.ad_board.http.controller;

import com.labs.ad_board.dto.AdFindOptionDto;
import com.labs.ad_board.service.AdService;
import com.labs.ad_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AdService adService;

    @GetMapping
    public String showUsersAds(Model model) {
        addUserToModel(model);

        model.addAttribute(
            "ads",
            adService.getAllSortingBy(AdFindOptionDto
                .builder()
                .username(userService.getUsernameOfCurrentUser())
                .build()
            )
        );
        return "user";
    }

    @PostMapping
    public String showUsersAds(AdFindOptionDto optionDto, Model model) {

        addUserToModel(model);

        model.addAttribute("ads", adService.getAllSortingBy(optionDto));
        model.addAttribute("selectedOptions", optionDto);
        return "user";
    }

    private void addUserToModel(Model model) {
        model.addAttribute(
            "user",
            userService.loadUserByUsername(
                userService.getUsernameOfCurrentUser()
            )
        );
    }
}
