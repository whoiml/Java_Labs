package com.labs.ad_board.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaptchaSettings {

    private String site;
    private String secret;
}
