package com.javarush.task.task30.task3008.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DmRG on 04.06.2017.
 */
public class ClientGuiModel {
    /*
   * Set<String> allUserNames - в нем будет храниться список всех участников чата.
   * */
    private final Set<String> allUserNames = new HashSet<>();

    /*
    * String newMessage, в котором будет храниться новое сообщение,
    * которое получил клиент.
    * */
    private String newMessage;

    /*
    * геттер для allUserNames, запретив модифицировать возвращенное
    * множество.
    * */
    public Set<String> getAllUserNames () {
        return Collections.unmodifiableSet(allUserNames);
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    /*
    *  метод void addUser(String newUserName), который должен добавлять
    *  имя участника во множество, хранящее всех участников.
    * */
    public void addUser(String newUserName) {
        allUserNames.add(newUserName);
    }

    /*
    * метод void deleteUser(String userName), который будет удалять имя
    * участника из множества.
    * */
    public void deleteUser(String userName) {
        allUserNames.remove(userName);
    }
}
