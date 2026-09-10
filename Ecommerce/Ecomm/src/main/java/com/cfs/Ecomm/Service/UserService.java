package com.cfs.Ecomm.Service;


import com.cfs.Ecomm.Model.User;
import com.cfs.Ecomm.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public User registerUser(User user){
        User newUser=userRepo.save(user);
        System.out.println("User added......");
        return newUser;
    }

    public User loginUser(String email,String password){
        User user=userRepo.findByEmail(email);
        if(user!=null &&  user.getPassword().equals(password)){

            return user;
        }
        return null;
    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }
}
