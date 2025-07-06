package com.grupo.proyectointegradori.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class pruebaEmail {
    @Autowired
    private EmailSenderService senderService;
    public static void main(String[] args){
        SpringApplication.run(pruebaEmail.class, args);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        senderService.sendEmail("coorpkdrisco.002@gmail.com", "example of subject", 
        "example of body");
    }
}
