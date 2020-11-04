package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.mail.MailSender;
import com.example.centrumtelefonii.models.MailInfo;
import com.example.centrumtelefonii.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
public class MailController {

    JavaMailSender javaMailSender;

    public MailController(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
    }

    void sendEmail(MailInfo mailInfo) throws MessagingException, IOException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("centrumtelefoniiolsztyn@gmail.com");
        msg.setSubject(mailInfo.getSubject());
        msg.setText("Mail: "+ mailInfo.getEmail()+ "\nTemat: " +mailInfo.getMessage());
        javaMailSender.send(msg);

    }

    @PostMapping(path = "/sendmail")
    public ResponseEntity<ApiResponse> sendingMail(@RequestBody MailInfo mailInfo){
        try{
            sendEmail(mailInfo);
            return ResponseEntity.ok(new ApiResponse(true, "Poprawnie wysłano maila"));
        }
        catch (MessagingException | IOException e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }
}
