package com.task.manager.service.Impl;

import com.task.manager.model.Project;
import com.task.manager.repository.ProjectRepository;
import com.task.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final String SUCCESS = "success";
    private static final String VALUE = "Data save successfully !";
    private static final String ID_NOT_FOUND = "idNotFound";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ERROR = "error";
    @Autowired
    public ProjectRepository projectRepository;
    @Override
    public Map<String, String> save(Map<String, String> map) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Project project = new Project();
            long duplicate = projectRepository.duplicateCheck(map.get("name"));
            if(duplicate>=1){
                msgMap.put(ERROR,"Name can't be duplicate !");
                return  msgMap;
            }
            project.setName(map.get("name"));
            projectRepository.save(project);
            msgMap.put(SUCCESS, VALUE);
            return msgMap;
        } catch (Exception ex) {
            msgMap.put(ERROR,"Name can't be duplicate !");
            return msgMap;
        }
    }
    @Override
    public Map getProjectList() {
        try {
            Map<String, List<Project>> rMap = new HashMap<>();
            List<Project> projectList = projectRepository.findAll();
            rMap.put("projectList", projectList);
            return rMap;
        } catch (Exception ex) {
            return null;
        }
    }
    @Override
    public Map<String, String> update(Map<String, String> dMap) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Project project = projectRepository.findById(Long.parseLong(dMap.get("projectId"))).get();
            if (project == null) {
                msgMap.put(ID_NOT_FOUND, "Project id not found !");
                return msgMap;
            }
            project.setName(dMap.get("name"));
            projectRepository.save(project);
            msgMap.put(UPDATE, "Project update successful !");
            return msgMap;
        } catch (Exception ex) {
            msgMap.put(ERROR, ex.getMessage());
            return msgMap;
        }
    }
    @Override
    public Map<String, String> delete(Map<String, String> dMap) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Project project = projectRepository.findById(Long.parseLong(dMap.get("projectId"))).get();
            if (project == null) {
                msgMap.put(ID_NOT_FOUND, "Project id not found !");
                return msgMap;
            }
            projectRepository.delete(project);
            msgMap.put(DELETE, "Project delete successful !");
            return msgMap;
        } catch (Exception ex) {
            msgMap.put("error", ex.getMessage());
            return msgMap;
        }
    }

    @Override
    public Project save(Project entity) {
        return projectRepository.save(entity);
    }

    @Override
    public Project update(Project entity) {
        return projectRepository.save(entity);
    }

    @Override
    public void delete(Project entity) {
        projectRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<Project> entities) {
        projectRepository.deleteInBatch(entities);
    }

    @Override
    public Project find(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            return project.get();
        }
        else{
            return null;
        }

    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByCreatedBy(String createdBy) {
        return projectRepository.findByCreatedBy(createdBy);
    }
}
