package com.labs.ad_board.http.controller;

import com.labs.ad_board.dto.AdCreateEditDto;
import com.labs.ad_board.dto.AdFindOptionDto;
import com.labs.ad_board.dto.AdReadDto;
import com.labs.ad_board.exception.UsernameException;
import com.labs.ad_board.service.AdService;
import com.labs.ad_board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ad")
@RequiredArgsConstructor
@Slf4j
public class AdController {

    private final AdService adService;
    private final UserService userService;

    @GetMapping("/create")
    public String getAllAds(Model model) {
        model.addAttribute("ad");
        return "createAd";
    }

    @PostMapping("/create")
    public String saveAd(@ModelAttribute("ad") AdCreateEditDto adCreateEditDto) {

        log.info(adCreateEditDto.toString());
        AdReadDto ad = adService.save(adCreateEditDto);
        log.info(ad.toString());
        return "redirect:/ad";
    }

    @GetMapping("/{id}")
    public String showAd(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ad", adService.getAdById(id));
        return "ad";
    }

    @PostMapping("/{id}/delete")
    public String deleteAd(@PathVariable("id") Integer id) {
        validateAdForCurrentUser(id);

        log.info("user " + userService.getUsernameOfCurrentUser() +
            " is trying to delete ad");

        adService.deleteAd(id);

        return "redirect:/ad";
    }

    @GetMapping("/{id}/edit")
    public String editAd(@PathVariable("id") Integer id, Model model) {
        validateAdForCurrentUser(id);

        model.addAttribute("ad", adService.getAdById(id));

        return "editAd";
    }

    @PostMapping("/{id}/edit")
    public String saveEditedAd(
        @PathVariable("id")
        Integer id,
        Model model,
        @ModelAttribute("ad")
        AdCreateEditDto adCreateEditDto) {

        log.info("Trying to edit ad with id: " + id);
        log.info(adCreateEditDto.toString());

        validateAdForCurrentUser(id);

        adService.update(id, adCreateEditDto);

        return "redirect:/ad/" + id;
    }

    @GetMapping
    public String showAllAds(Model model) {
        model.addAttribute("ads", adService.getAllAds());
        return "ads";
    }

    @PostMapping
    public String showAllAds(AdFindOptionDto optionDto, Model model) {
        model.addAttribute("ads", adService.getAllSortingBy(optionDto));
        model.addAttribute("selectedOptions", optionDto);
        return "ads";
    }

    private void validateAdForCurrentUser(int id) {
        String adOwner = adService.getAdById(id)
            .getUsername();

        if (!adOwner.equalsIgnoreCase(
            userService.getUsernameOfCurrentUser()
        )) {
            throw new UsernameException("You are not owner of this ad!!");
        }
    }
}
