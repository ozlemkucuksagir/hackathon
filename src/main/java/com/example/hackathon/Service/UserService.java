package com.example.hackathon.Service;

import com.example.hackathon.Entity.User;
import com.example.hackathon.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
   /* @Autowired
    UserRepository userRepository;*/

    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByID(Long user_id) {
        return userRepository.findById(user_id).get();

    }

    public void deleteUserByID(Long user_id){
        userRepository.deleteById(user_id);
    }

    public void createUser(User new_user){

        userRepository.save(new_user);
    }
    public void updateUser(User updated_user, Long user_id){
        User user;
        user=userRepository.findById(user_id).get();
        user.setId(updated_user.getId());
        user.setEmail(updated_user.getEmail());
        user.setName(updated_user.getName());
        userRepository.save(user);

    }



}
