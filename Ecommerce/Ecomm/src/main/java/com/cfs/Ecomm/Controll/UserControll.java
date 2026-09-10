package com.cfs.Ecomm.Controll;

import com.cfs.Ecomm.Model.User;
import com.cfs.Ecomm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControll {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user){
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    @GetMapping
    public List<User> getAlluser(){
        return userService.getAllUser();
    }

}
