package com.zach.ZakksTasks.repository;

import com.zach.ZakksTasks.model.Task;
import com.zach.ZakksTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
