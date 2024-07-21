package com.example.hackathon.Service;

import com.example.hackathon.Entity.User;
import com.example.hackathon.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByID(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    public void deleteUserByID(Long user_id) {
        userRepository.deleteById(user_id);
    }

    public void createUser(User new_user) {
        userRepository.save(new_user);
    }

    public void updateUser(User updated_user, Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(updated_user.getUsername());
            user.setEmail(updated_user.getEmail());
            user.setPassword(updated_user.getPassword());
            userRepository.save(user);
        }
    }

    public UserDetails loadUserByUsername(String username){
        return userRepository.findByUsername(username);

    }
}
