package com.labs.ad_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdCreateEditDto {

    String adName;
    String description;
    String theme;
}
