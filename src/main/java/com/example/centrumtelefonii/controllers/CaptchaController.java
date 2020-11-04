package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.auth.CaptchaValidator;
import com.example.centrumtelefonii.models.CaptchaToken;
import com.example.centrumtelefonii.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {

    CaptchaValidator captchaValidator;

    public CaptchaController(CaptchaValidator captchaValidator) {
        this.captchaValidator = captchaValidator;
    }

    @RequestMapping(path = "/captchavalidation")
    public ResponseEntity<ApiResponse> validateCaptcha(@RequestBody CaptchaToken token)
    {
        boolean isValidCaptcha = captchaValidator.validateCaptcha(token.getToken());
        if(!isValidCaptcha){
            return ResponseEntity.ok(new ApiResponse(false, "Poprawna captcha"));
        }
        else{
            return ResponseEntity.ok(new ApiResponse(true, "Poprawna captcha"));
        }
    }

}
