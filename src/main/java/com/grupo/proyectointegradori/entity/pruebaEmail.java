package com.grupo.proyectointegradori.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class pruebaEmail {
    @Autowired
    private EmailSenderService senderService;

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        senderService.sendEmail("u22238363@utp.edu.pe", "Prueba", 
        "hola");
    }
}
