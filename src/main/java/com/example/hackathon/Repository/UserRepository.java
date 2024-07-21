package com.example.hackathon.Repository;

import com.example.hackathon.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    List<User> findAllById(Iterable<Long> longs);
}
