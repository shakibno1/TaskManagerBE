package com.task.manager.service;

import com.task.manager.model.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService extends GenericService<Project> {

    List<Project> findByCreatedBy(String createdBy);
    Map<String, String> save(Map<String, String> map);
    Map getProjectList();
    Map<String, String> update(Map<String, String> dMap);
    Map<String, String> delete(Map<String, String> dMap);

}
