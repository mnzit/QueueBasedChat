/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mnzit.wesbocket.queuebasedchat.controller;

import com.mnzit.wesbocket.queuebasedchat.db.ClientContainer;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Mnzit
 */
@Controller
public class ChatController {

    @Autowired
    private ClientContainer container;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping("/participants")
    public List<String> retrieveParticipants() {
        return container.getAll();
    }

    @MessageMapping("/private/message/{username}")
    public void filterPrivateMessage(@Payload String message, @DestinationVariable("username") String username, Principal principal) {
        simpMessagingTemplate.convertAndSend("/user/" + username + "/queue/private", message);
    }
}
