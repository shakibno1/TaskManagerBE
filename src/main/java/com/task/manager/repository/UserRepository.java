package com.task.manager.repository;

import com.task.manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
  User findUserById(Long id);
  @Query(value = "SELECT count(*)  FROM application_user as au \n" +
          "WHERE au.username LIKE(:username)", nativeQuery = true)
  int duplicateCheck(@Param("username") String username);
  @Query(value = "SELECT \n"
          + "au.id applicaion_user_id,\n"
          + "GROUP_CONCAT(ro.name,',') role_name,\n"
          + "au.username user_name \n"
          + "FROM user_role ur\n"
          + "INNER JOIN application_user au\n"
          + "ON au.id = ur.user_id\n"
          + "INNER JOIN role ro\n"
          + "ON ro.id = ur.role_id\n"
          + "GROUP BY au.username,au.id",nativeQuery = true)
  List<Map<?,?>> roleMappingList();
}
