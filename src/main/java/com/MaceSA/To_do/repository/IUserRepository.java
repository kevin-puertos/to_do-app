package com.MaceSA.To_do.repository;

import com.MaceSA.To_do.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long > {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userTasks")
    List<User> userWithTask();


}
