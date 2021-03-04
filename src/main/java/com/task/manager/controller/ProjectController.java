package com.task.manager.controller;

import com.task.manager.model.Project;
import com.task.manager.model.Task;
import com.task.manager.model.User;
import com.task.manager.service.Impl.UserDetailsServiceImpl;
import com.task.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    public ProjectService projectService;
    @Autowired
    public UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping(value = "/save")
    public Object save(@RequestBody Project project) {
        projectService.save(project);
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("success", "Data saved successfully");
        return msgMap;
    }

    @GetMapping(value = "/list")
    public Object getProjectList(@RequestParam(required = false) String param) {
        User principal;
        principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return projectService.findByCreatedBy(principal.getUsername());

    }

    @GetMapping(value = "/listByUser")
    public Object getProjectListByUser(@RequestParam(required = false) String param) {
        User principal;
        List<Project> projectList = new ArrayList<>();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(param)){
            param = param.substring(0,param.length()-1);
            String [] usernames = param.split(",");
            for(String username : usernames){
                projectList.addAll(projectService.findByCreatedBy(username));
            }
            return projectList;
        }
        return projectList;
    }

    @PostMapping(value = "/update")
    public Object update(@RequestBody Project project) {
        return projectService.update(project);
    }

    @PostMapping(value = "/delete")
    public Object delete(@RequestBody Project project) {
        projectService.delete(project);
        return "Successfully deleted";
    }
}
