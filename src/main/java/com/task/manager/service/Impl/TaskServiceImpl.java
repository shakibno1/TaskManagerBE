package com.task.manager.service.Impl;

import com.task.manager.model.Project;
import com.task.manager.model.Status;
import com.task.manager.model.Task;
import com.task.manager.repository.ProjectRepository;
import com.task.manager.repository.TaskRepository;
import com.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private static final String SUCCESS = "success";
    private static final String VALUE = "Data save successfully !";
    private static final String ID_NOT_FOUND = "idNotFound";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ERROR = "error";
    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public ProjectRepository projectRepository;

    @Override
    public Map<String, String> save(Map<String, String> map) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Task task = new Task();
            long duplicate = taskRepository.duplicateCheck(map.get("description"));
            if(duplicate>=1){
                msgMap.put(ERROR,"Description can't be duplicate !");
                return  msgMap;
            }
            Optional<Project> projects = projectRepository.findById(Long.parseLong(map.get("projectId")));
            if(map.get("status").equals("open")){
                task.setStatus(Status.OPEN);
            }
            else if(map.get("status").equals("in_progress")){
                task.setStatus(Status.IN_PROGRESS);
            }
            else if(map.get("status").equals("closed")){
                task.setStatus(Status.CLOSED);
            }
            task.setDescription(map.get("description"));
            task.setProject(projects.isPresent()?projects.get():null);
            Date dueDate = Date.valueOf(map.get("dueDate"));
            task.setDueDate(dueDate);
            taskRepository.save(task);
            msgMap.put(SUCCESS, VALUE);
            return msgMap;
        } catch (Exception ex) {
            msgMap.put(ERROR,"Description can't be duplicate !");
            return msgMap;
        }
    }

    @Override
    public Map getTaskList() {
        try {
            Map<String, List<Task>> rMap = new HashMap<>();
            List<Task> taskList = taskRepository.findAll();
            rMap.put("taskList", taskList);
            return rMap;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Map<String, String> update(Map<String, String> dMap) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Task task = taskRepository.findById(Long.parseLong(dMap.get("taskId"))).get();
            if (task == null) {
                msgMap.put(ID_NOT_FOUND, "Task id not found !");
                return msgMap;
            }
            if(task.getStatus() != Status.CLOSED){
                Optional<Project> projects = projectRepository.findById(Long.parseLong(dMap.get("projectId")));
                if(dMap.get("status").equals("open")){
                    task.setStatus(Status.OPEN);
                }
                else if(dMap.get("status").equals("in_progress")){
                    task.setStatus(Status.IN_PROGRESS);
                }
                else if(dMap.get("status").equals("closed")){
                    task.setStatus(Status.CLOSED);
                }
                task.setDescription(dMap.get("description"));
                task.setProject(projects.isPresent()?projects.get():null);
                Date dueDate = Date.valueOf(dMap.get("dueDate"));
                task.setDueDate(dueDate);
                taskRepository.save(task);
                msgMap.put(UPDATE, "Task update successful !");
            }
            else{
                msgMap.put(ERROR, "Closed task can't be updated !");
            }

            return msgMap;
        } catch (Exception ex) {
            msgMap.put(ERROR, ex.getMessage());
            return msgMap;
        }
    }

    @Override
    public List<Task> findByProject(Project project) {
        return taskRepository.findByProject(project);
    }

    @Override
    public List<Task> findByStatusAndCreatedBy(Status status,String createdBy) {
        return taskRepository.findByStatusAndCreatedBy(status,createdBy);
    }

    @Override
    public List<Task> findByCreatedByAndDueDateBefore(String createdBy, Date currentDate) {
        return taskRepository.findByCreatedByAndDueDateBefore(createdBy,currentDate);
    }

    @Override
    public List<Task> findByCreatedBy(String createdBy) {
        return taskRepository.findByCreatedBy(createdBy);
    }

    @Override
    public Map<String, String> delete(Map<String, String> dMap) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Task task = taskRepository.findById(Long.parseLong(dMap.get("taskId"))).get();
            if (task == null) {
                msgMap.put(ID_NOT_FOUND, "Task id not found !");
                return msgMap;
            }
            taskRepository.delete(task);
            msgMap.put(DELETE, "Task delete successful !");
            return msgMap;
        } catch (Exception ex) {
            msgMap.put("error", ex.getMessage());
            return msgMap;
        }
    }

    @Override
    public Task save(Task entity) {
        return taskRepository.save(entity);
    }

    @Override
    public Task update(Task entity) {
        return taskRepository.save(entity);
    }

    @Override
    public void delete(Task entity) {
        taskRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<Task> entities) {
        taskRepository.deleteInBatch(entities);
    }

    @Override
    public Task find(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            return task.get();
        }
        else{
            return null;
        }
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
