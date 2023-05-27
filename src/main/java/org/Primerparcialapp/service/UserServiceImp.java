package org.Primerparcialapp.service;


import org.Primerparcialapp.model.User;
import org.Primerparcialapp.repository.UserRepository;
import org.Primerparcialapp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;



    public User getUserById(Long id){return userRepository.findById(id).get();}


    public List<User> allUser() {
        return userRepository.findAll();
    }

    @Override
    public Boolean CreateUser(User user) {
        Optional<User> userBd = userRepository.findByMail(user.getMail());
        if(userBd.isEmpty()){
            try {
                User userBD=user;
                userBD.setName(user.getName());
                userBD.setLastname(user.getLastname());
                userBD.setDocument(user.getDocument());
                userBD.setCellphone(user.getCellphone());
                userBD.setBirthday(user.getBirthday());
                userBD.setMail(user.getMail());
                userRepository.save(userBD);
                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean updateUser(Long id,User user){
        Optional<User> userBd = userRepository.findById(id);
        try {
            User userBD= userBd.get();
            userBD.setName(user.getName());
            userBD.setLastname(user.getLastname());
            userBD.setDocument(user.getDocument());
            userBD.setCellphone(user.getCellphone());
            userBD.setBirthday(user.getBirthday());
            userBD.setMail(user.getMail());
            User userUp=userRepository.save(userBD);
            return true;
    }catch (Exception e){
        return false;
    }
    }
    @Override
    public String login(User user) {
        Optional<User> userBd = userRepository.findByMail(user.getMail());
        if(userBd.isEmpty()){
            throw new RuntimeException("Usuario no encontrado!");
        }

        if(!userBd.get().getPassword().equals(user.getPassword())){
            throw new RuntimeException("La contraseña es incorrecta!");
        }
        return jwtUtil.create(String.valueOf(userBd.get().getId()),
                String.valueOf(userBd.get().getMail()));
    }

}