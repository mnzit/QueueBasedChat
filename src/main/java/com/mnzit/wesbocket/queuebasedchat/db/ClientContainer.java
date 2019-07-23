/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mnzit.wesbocket.queuebasedchat.db;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mnzit
 */
public class ClientContainer {

    List<String> users = new ArrayList<>();

    public void add(String user) {
        users.add(user);
    }

    public void remove(String user) {
        users.remove(user);
    }

    public List<String> getAll() {
        return users;
    }
}
