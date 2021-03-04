package com.task.manager.repository;

import com.task.manager.model.Project;
import com.task.manager.model.Status;
import com.task.manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query(value = "SELECT count(*) FROM task\n"
            +"WHERE UPPER(description)=UPPER(:description)",nativeQuery = true)
    long duplicateCheck(@Param("description") String description);

    Task findByDescription(String description);

    List<Task> findByCreatedBy(String createdBy);

    List<Task> findByProject(Project project);

    List<Task> findByStatusAndCreatedBy(Status status,String createdBy);

    List<Task> findByCreatedByAndDueDateBefore(String createdBy , Date currentDate);
}
