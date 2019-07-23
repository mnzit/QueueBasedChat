/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mnzit.wesbocket.queuebasedchat.event;

import com.mnzit.wesbocket.queuebasedchat.db.ClientContainer;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 * @author Mnzit
 */
public class UserPresenceListener {
    
    private ClientContainer container;

    public UserPresenceListener(ClientContainer container) {
        this.container = container;
    }
      
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        String user = event.getUser().getName();
        
        container.add(user);
    }
    
    @EventListener
    private void handleSessionDisconnected(SessionDisconnectEvent event) {
        String user = event.getUser().getName();
        container.remove(user);
    }
}
