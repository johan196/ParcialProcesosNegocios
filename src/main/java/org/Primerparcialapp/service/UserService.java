package org.Primerparcialapp.service;

import org.Primerparcialapp.model.User;

import javax.swing.event.ListDataEvent;
import java.util.List;

public interface UserService {
    User getUserById(Long id);
    Boolean CreateUser(User user);
    List<User>allUser();
    Boolean updateUser(Long id,User user);
    String login(User user);

}
