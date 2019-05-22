package com.example.easynotes.repository;

import com.example.easynotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select t from User t where t.name = ?1 and t.password=?2")
    User findUser(String name, String password);
}
