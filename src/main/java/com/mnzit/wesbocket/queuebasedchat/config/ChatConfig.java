/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mnzit.wesbocket.queuebasedchat.config;

import com.mnzit.wesbocket.queuebasedchat.db.ClientContainer;
import com.mnzit.wesbocket.queuebasedchat.event.UserPresenceListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Mnzit
 */
@Configuration
public class ChatConfig {

    @Bean
    public ClientContainer getContainer() {
        return new ClientContainer();
    }

    @Bean
    public UserPresenceListener presenceEventListener() {
        UserPresenceListener listener = new UserPresenceListener(getContainer());
        return listener;
    }
}
