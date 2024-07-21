package com.example.hackathon.Controller;

import com.example.hackathon.Entity.User;
import com.example.hackathon.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

   @GetMapping("/getAllUsers")
    List<User> getAllUsers (){
        return userService.getAllUsers();
    }

    @GetMapping("/getUserByID/{user_id}")
    User getUserByID (@PathVariable Long user_id){
        return userService.getUserByID(user_id);
    }

    @PutMapping("/update-user/{user_id}")
    public void update_user(@RequestBody User updated_user,@PathVariable Long user_id){
        userService.updateUser(updated_user,user_id);
    }

    @PostMapping("/create-user")
    public void create_user(@RequestBody User new_user){
        userService.createUser(new_user);
    }

    @DeleteMapping("/delete-user/{user_id}")
    public void delete_user(@PathVariable Long user_id){
        userService.deleteUserByID(user_id);

    }
}
