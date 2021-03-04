package com.task.manager.service.Impl;

import com.task.manager.model.Role;
import com.task.manager.repository.RoleRepository;
import com.task.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private static final String SUCCESS = "success";
    private static final String VALUE = "Data save successfully !";
    private static final String ID_NOT_FOUND = "idNotFound";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ERROR = "error";
    @Autowired
    public RoleRepository roleRepository;
    @Override
    public Map<String, String> save(Map<String, String> map) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Role role = new Role();
            long duplicate = roleRepository.duplicateCheck(map.get("name"));
            if(duplicate>=1){
                msgMap.put(ERROR,"Name can't be duplicate !");
                return  msgMap;
            }
            role.setName(map.get("name"));
            role.setRemarks(map.get("remarks"));
            roleRepository.save(role);
            msgMap.put(SUCCESS, VALUE);
            return msgMap;
        } catch (Exception ex) {
            msgMap.put(ERROR,"Name can't be duplicate !");
            return msgMap;
        }
    }
    @Override
    public Map getRoleList() {
        try {
            Map<String, List<Role>> rMap = new HashMap<>();
            List<Role> roleList = roleRepository.findAll();
            rMap.put("roleList", roleList);
            return rMap;
        } catch (Exception ex) {
            return null;
        }
    }
    @Override
    public Map<String, String> update(Map<String, String> dMap) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            Role role = roleRepository.findById(Long.parseLong(dMap.get("roleId"))).get();
            if (role == null) {
                msgMap.put(ID_NOT_FOUND, "Role id not found !");
                return msgMap;
            }
            long duplicate = roleRepository.findByNameAndIdNotEqual(dMap.get("name"),Long.parseLong(dMap.get("roleId")));
            if(duplicate>=1){
                msgMap.put(ERROR,"Name can't be duplicate !");
                return  msgMap;
            }
            role.setName(dMap.get("name"));
            role.setRemarks(dMap.get("remarks"));
            roleRepository.save(role);
            msgMap.put(UPDATE, "Role update successful !");
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
            Role role = roleRepository.findById(Long.parseLong(dMap.get("roleId"))).get();
            if (role == null) {
                msgMap.put(ID_NOT_FOUND, "Role id not found !");
                return msgMap;
            }
            roleRepository.delete(role);
            msgMap.put(DELETE, "Role delete successful !");
            return msgMap;
        } catch (Exception ex) {
            msgMap.put("error", ex.getMessage());
            return msgMap;
        }
    }

    @Override
    public Role save(Role entity) {
        return roleRepository.save(entity);

    }

    @Override
    public Role update(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void delete(Role entity) {
        roleRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<Role> entities) {
        roleRepository.deleteAll(entities);
    }

    @Override
    public Role find(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }
        else{
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
