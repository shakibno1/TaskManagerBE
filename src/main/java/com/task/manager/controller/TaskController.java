package com.task.manager.controller;

import com.task.manager.model.Status;
import com.task.manager.model.Task;
import com.task.manager.model.User;
import com.task.manager.service.ProjectService;
import com.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    public TaskService taskService;
    @Autowired
    public ProjectService projectService;

    @PostMapping(value = "/save")
    public Map<String, String> save(@RequestBody Map<String, String> reqMap) {
        return taskService.save(reqMap);
    }

    @GetMapping(value = "/list")
    public Object getTaskList() {
        User principal;
        principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskService.findByCreatedBy(principal.getUsername());
    }
    @GetMapping(value = "/listByExpiredTask")
    public Object getExpiredTaskList() {
        User principal;
        principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskService.findByCreatedByAndDueDateBefore(principal.getUsername(),new Date(System.currentTimeMillis()));
    }
    @GetMapping(value = "/listByUser")
    public Object getTaskListByUser(@RequestParam(required = false) String param) {
        List<Task> taskList = new ArrayList<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(param)){
            param = param.substring(0,param.length()-1);
            String [] usernames = param.split(",");
            for(String username : usernames){
                taskList.addAll(taskService.findByCreatedBy(username));
            }
            return taskList;
        }
        return taskList;
    }
    @GetMapping(value = "/listByProject")
    public Object getTaskListByProject(@RequestParam(required = false) String param) {
        List<Task> taskList = new ArrayList<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(param)){
            param = param.substring(0,param.length()-1);
            String [] projectIds = param.split(",");
            for(String projectId : projectIds){
                taskList.addAll(taskService.findByProject(projectService.find(Long.parseLong(projectId))));
            }
            return taskList;
        }
        return taskList;
    }
    @GetMapping(value = "/listByStatus")
    public Object getTaskListByStatus(@RequestParam(required = false) String param) {
        List<Task> taskList = new ArrayList<>();
        User principal;
        principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(param)){
            param = param.substring(0,param.length()-1);
            String [] status = param.split(",");
            for(String name : status){
                if(name.equals("open")){
                    taskList.addAll(taskService.findByStatusAndCreatedBy(Status.OPEN,principal.getUsername()));
                }
                else if(name.equals("in_progress")){
                    taskList.addAll(taskService.findByStatusAndCreatedBy(Status.IN_PROGRESS,principal.getUsername()));
                }
                else if(name.equals("closed")){
                    taskList.addAll(taskService.findByStatusAndCreatedBy(Status.CLOSED,principal.getUsername()));
                }
            }
        }
        return taskList;
    }
    @PostMapping(value = "/update")
    public Map<String, String> update(@RequestBody Map<String, String> dMap) {
        return taskService.update(dMap);
    }

    @PostMapping(value = "/delete")
    public Map<String, String> delete(@RequestBody Map<String, String> dMap) {
        return taskService.delete(dMap);
    }
}
