package org.Primerparcialapp.service;


import org.Primerparcialapp.model.User;
import org.Primerparcialapp.repository.UserRepository;
import org.Primerparcialapp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;



    public User getUserById(Long id){return userRepository.findById(id).get();}


    public List<User> allUser() {
        return userRepository.findAll();
    }

    @Override
    public Boolean CreateUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean updateUser(Long id,User user){
        try {

            User userBD=userRepository.findById(id).get();
        userBD.setName(user.getName());
        userBD.setLastname(user.getLastname());
        userBD.setDocument(user.getDocument());
        userBD.setCellphone(user.getCellphone());
        userBD.setBirthday(user.getBirthday());
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

        if(!passwordEncoder.matches(user.getPassword(), userBd.get().getPassword())){
            throw new RuntimeException("La contraseña es incorrecta!");
        }
        return jwtUtil.create(String.valueOf(userBd.get().getId()),
                String.valueOf(userBd.get().getMail()));
    }

}