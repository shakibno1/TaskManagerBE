package com.task.manager.controller;

import com.task.manager.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reset")
public class ResetPasswordController {
    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/resetPassword")
    public Map<String, String> resetPassword(@RequestParam Map<String, String> dMap) {
        return resetPasswordService.resetPassword(dMap);
    }

    @GetMapping("/restHistoryList")
    public List<Map<?, ?>> restHistoryList() {
        return resetPasswordService.getResetPasswordHistory();
    }
}
