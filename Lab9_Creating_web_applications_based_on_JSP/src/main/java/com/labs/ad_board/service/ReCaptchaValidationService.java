package com.labs.ad_board.service;
import com.labs.ad_board.dto.ReCaptchResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReCaptchaValidationService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.key.secret}")
    private String RECAPTCHA_SECRET;

    public boolean validateCaptcha(String captchaResponse){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", RECAPTCHA_SECRET);
        requestMap.add("response", captchaResponse);

        ReCaptchResponseType apiResponse = restTemplate.postForObject(
                GOOGLE_RECAPTCHA_ENDPOINT,
                requestMap,
                ReCaptchResponseType.class
        );

        log.info("Response is: " + apiResponse);

        if(apiResponse == null){
            log.info("Captcha response is null");
            return false;
        }

        return Boolean.TRUE.equals(apiResponse.isSuccess());
    }
}