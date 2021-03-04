package com.task.manager.service;

import com.task.manager.model.Project;
import com.task.manager.model.Status;
import com.task.manager.model.Task;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TaskService extends GenericService<Task> {

    List<Task> findByCreatedBy(String createdBy);
    Map<String, String> delete(Map<String, String> dMap);
    Map<String, String> save(Map<String, String> map);
    Map getTaskList();
    Map<String, String> update(Map<String, String> dMap);
    List<Task> findByProject(Project project);
    List<Task> findByStatusAndCreatedBy(Status status, String createdBy);
    List<Task> findByCreatedByAndDueDateBefore(String createdBy , Date currentDate);
}
