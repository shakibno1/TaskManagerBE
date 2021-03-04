package com.task.manager.service.Impl;

import com.task.manager.controller.AuthenticationController;
import com.task.manager.model.Role;
import com.task.manager.model.User;
import com.task.manager.repository.RoleRepository;
import com.task.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String SUCCESS_MESSAGE = "success";
  private static final String USER_LIST = "userList";
  private static final String USER_EXIT = "userExit";
  private static final String SUCCESS = "success";
  private static final String ERROR = "error";

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder bcryptEncoder;


  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(userName);
    if(user == null){
      throw new UsernameNotFoundException(userName);
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    user.getRoles().forEach( (role) -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
    user.setAuthorities(grantedAuthorities);

    AuthenticationController.setUser(user);

   /* for (Role role : user.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
    }*/

    return user;

  }

  public Map<String,String> save(User user) {
    Map<String,String> msgMap = new HashMap<>();
    try {
      User applicationUser = new User();
      int dupUsernameCheck = userRepository.duplicateCheck(user.getUsername());
      if (dupUsernameCheck >= 1) {
        msgMap.put(USER_EXIT,user.getUsername() + " " + "already exits");
      } else {
        applicationUser.setUsername(user.getUsername());
        applicationUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        applicationUser.setEmail(user.getEmail());
        userRepository.save(applicationUser);
        msgMap.put(SUCCESS_MESSAGE,"Data Save Successfully");
        return  msgMap;
      }
    } catch (Exception ex) {
      msgMap.put("error",ex.getMessage());
      return msgMap;
    }
    return  msgMap;
  }

  public Map<String, List<Map<String,Object>>> getUserList() {
    Map<String, List<Map<String,Object>>> dMap = new HashMap<>();
    try {
      List<User> userList = userRepository.findAll();
      List<Map<String,Object>> usernames = new ArrayList<>();
      for (User user : userList){
        Map map = new HashMap();
        map.put("username",user.getUsername());
        map.put("email",user.getEmail());
        map.put("id",user.getId());
        usernames.add(map);
      }
      dMap.put(USER_LIST, usernames);
      return dMap;
    } catch (Exception ex) {
      throw new RuntimeException();
    }
  }

  public Map<String, String> saveUserRoleMap(Map<String, String> dMap) {
    Map<String, String> msgMap = new HashMap<>();
    try {
      User user = userRepository.findByUsername(dMap.get("username"));
      String[] roleArray = dMap.get("roleList").split(",");
      Set<Role> roles = new HashSet<Role>();
      for (int i = 0; i < roleArray.length; i++) {
        Role role = roleRepository.findByName(roleArray[i]);
        roles.add(role);
      }
      user.setRoles(roles);
      userRepository.save(user);
      msgMap.put(SUCCESS, "User role mapping successful !");
      return msgMap;
    } catch (Exception ex) {
      msgMap.put(ERROR, ex.getMessage());
      return msgMap;
    }
  }

  public List<Map<?, ?>> getRoleMappingList() {
    return userRepository.roleMappingList();
  }

  public User getUserByUserId (Long id){
    return userRepository.findUserById(id);
  }

}
