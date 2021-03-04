package com.task.manager.controller;

import com.task.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    public RoleService roleService;

    @PostMapping(value = "/save")
    public Map<String, String> save(@RequestBody Map<String, String> reqMap) {
        return roleService.save(reqMap);
    }

    @GetMapping(value = "/list")
    public Map getRoleList() {
        return roleService.getRoleList();
    }

    @PostMapping(value = "/update")
    public Map<String, String> update(@RequestBody Map<String, String> dMap) {
        return roleService.update(dMap);
    }

    @PostMapping(value = "/delete")
    public Map<String, String> delete(@RequestBody Map<String, String> dMap) {
        return roleService.delete(dMap);
    }
}
