package com.labs.ad_board.mapper;

import com.labs.ad_board.db.entity.Ad;
import com.labs.ad_board.dto.AdCreateEditDto;
import com.labs.ad_board.dto.AdReadDto;
import com.labs.ad_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AdMapper {

    private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";

    private final UserService userService;

    public Ad map(AdCreateEditDto adDto) {
        return Ad.builder()
                .userId(
                    userService.getUserIdByUsername(
                        userService.getUsernameOfCurrentUser()
                    )
                )
                .adName(adDto.getAdName())
                .description(adDto.getDescription())
                .theme(adDto.getTheme())
                .creationDate(LocalDateTime.now())
                .build();
    }


    public AdReadDto map(Ad ad) {
        return AdReadDto.builder()
            .id(ad.getId())
            .username(userService.getUsernameByUserId(ad.getUserId()))
            .adName(ad.getAdName())
            .description(ad.getDescription())
            .theme(ad.getTheme())
            .creationDate(ad.getCreationDate()
                .format(
                    DateTimeFormatter.ofPattern(DATE_FORMATTER)
            ))
            .build();
    }
}
