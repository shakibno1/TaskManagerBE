package com.task.manager.service;

import com.task.manager.model.ResetPasswordHistory;
import com.task.manager.model.User;
import com.task.manager.repository.RestPasswordHistoryRepository;
import com.task.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ResetPasswordService extends GenericService<ResetPasswordHistory> {

    Map<String, String> resetPassword(Map<String, String> dMap);
    List<Map<?,?>> getResetPasswordHistory();

}
