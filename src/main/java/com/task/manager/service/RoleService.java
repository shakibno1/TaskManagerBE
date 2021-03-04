package com.task.manager.service;

import com.task.manager.model.Role;

import java.util.Map;

public interface RoleService extends GenericService<Role> {

    Map<String, String> save(Map<String, String> map);
    Map getRoleList();
    Map<String, String> update(Map<String, String> dMap);
    Map<String, String> delete(Map<String, String> dMap);

}
