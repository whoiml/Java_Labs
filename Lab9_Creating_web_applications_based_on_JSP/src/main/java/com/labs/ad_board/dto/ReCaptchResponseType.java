package com.labs.ad_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReCaptchResponseType {

    private boolean success;
    private String challenge_ts;
    private String hostname;
}