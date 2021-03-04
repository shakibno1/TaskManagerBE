package com.task.manager.repository;

import com.task.manager.model.ResetPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RestPasswordHistoryRepository extends JpaRepository<ResetPasswordHistory,Long> {

    @Query(value = "SELECT rph.id, au.username,rph.created_date AS change_date,\n"
            + "rph.created_by change_by,rph.remarks\n"
            + "FROM application_user au\n"
            + "INNER JOIN reset_password_history rph\n"
            + "ON rph.application_user_id = au.id",nativeQuery = true)
    List<Map<?,?>> getHistoryList();
}
