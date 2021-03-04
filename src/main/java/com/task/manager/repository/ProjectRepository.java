package com.task.manager.repository;

import com.task.manager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query(value = "SELECT count(*) FROM project\n"
            +"WHERE UPPER(name)=UPPER(:name)",nativeQuery = true)
    long duplicateCheck (@Param("name") String name);

    Project findByName(String name);

    List<Project> findByCreatedBy(String createdBy);
}
