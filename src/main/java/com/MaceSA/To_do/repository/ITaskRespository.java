package com.MaceSA.To_do.repository;

import com.MaceSA.To_do.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRespository extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE t.user.id = :id")
    List<Task> viewAllUserTask(@Param("id") Long id);

}
