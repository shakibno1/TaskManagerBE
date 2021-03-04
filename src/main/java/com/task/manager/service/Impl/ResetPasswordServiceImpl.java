package com.task.manager.service.Impl;

import com.task.manager.model.ResetPasswordHistory;
import com.task.manager.model.User;
import com.task.manager.repository.RestPasswordHistoryRepository;
import com.task.manager.repository.UserRepository;
import com.task.manager.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestPasswordHistoryRepository restPasswordHistoryRepository;
    @Override
    public Map<String, String> resetPassword(Map<String, String> dMap) {
        Map<String, String> msgMap = new HashMap<>();
        try {
            User user = userRepository.findByUsername(dMap.get("username"));
            user.setPassword(passwordEncoder.encode(dMap.get("password")));
            userRepository.save(user);

            /* Note : This code is for password change history */
            ResetPasswordHistory resetPasswordHistory = new ResetPasswordHistory();
            resetPasswordHistory.setApplicationUserId(user.getId());
            resetPasswordHistory.setRemarks(dMap.get("remarks"));
            restPasswordHistoryRepository.save(resetPasswordHistory);
            msgMap.put(SUCCESS, "Successfully rest password !");
            return msgMap;
        } catch (Exception ex) {
            msgMap.put(ERROR, ex.getMessage());
            return msgMap;
        }
    }
    @Override
    public List<Map<?,?>> getResetPasswordHistory() {
        return restPasswordHistoryRepository.getHistoryList();
    }

    @Override
    public ResetPasswordHistory save(ResetPasswordHistory entity) {
        return restPasswordHistoryRepository.save(entity);
    }

    @Override
    public ResetPasswordHistory update(ResetPasswordHistory entity) {
        return restPasswordHistoryRepository.save(entity);
    }

    @Override
    public void delete(ResetPasswordHistory entity) {
        restPasswordHistoryRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        restPasswordHistoryRepository.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<ResetPasswordHistory> entities) {
        restPasswordHistoryRepository.deleteInBatch(entities);
    }

    @Override
    public ResetPasswordHistory find(Long id) {
        Optional<ResetPasswordHistory> resetPasswordHistory = restPasswordHistoryRepository.findById(id);
        if(resetPasswordHistory.isPresent()){
            return resetPasswordHistory.get();
        }
        else{
            return null;
        }
    }

    @Override
    public List<ResetPasswordHistory> findAll() {
        return restPasswordHistoryRepository.findAll();
    }
}
