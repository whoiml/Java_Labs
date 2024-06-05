package com.labs.ad_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdReadDto {

    int id;
    String username;
    String adName;
    String description;
    String creationDate;
    String theme;
}
