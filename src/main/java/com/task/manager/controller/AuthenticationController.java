package com.task.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.manager.common.Constants;
import com.task.manager.config.JwtTokenUtil;
import com.task.manager.model.Role;
import com.task.manager.model.User;
import com.task.manager.model.JwtRequest;
import com.task.manager.service.Impl.UserDetailsServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static User user;

    public static void setUser(User user) {
        AuthenticationController.user = user;
    }

    @PostMapping(Constants.LOG_IN)
    public ResponseEntity<String> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        JSONObject map = new JSONObject();
        map.put("username", userDetails.getUsername());
        map.put("password", userDetails.getPassword());
        map.put("token", token);
        ArrayList<String> rolesArr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Set<Role> roles =  user.getRoles();
        for (Role role:roles){
            sb.append(role.getName()+",");
        }
        ObjectMapper mapper = new ObjectMapper();
        map.put("role", sb);
        return new ResponseEntity<>(map.toString(), HttpStatus.OK);
    }

    @PostMapping(Constants.SIGN_UP)
    public Map<String,String> saveUser(@RequestBody User applicationUser) {
        return userDetailsService.save(applicationUser);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping(value = "user/list")
    public Map<String, List<Map<String,Object>>> userList(){
        return userDetailsService.getUserList();
    }

    @PostMapping("/userRoleMap")
    public Map<String, String> save(@RequestParam Map<String, String> dMap) {
        return userDetailsService.saveUserRoleMap(dMap);
    }

    @GetMapping("/userRoleMaps")
    public List<Map<?, ?>> getUserRoleMappingList() {
        return userDetailsService.getRoleMappingList();
    }
}
