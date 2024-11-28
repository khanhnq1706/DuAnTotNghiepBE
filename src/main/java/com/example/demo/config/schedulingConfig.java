package com.example.demo.config;

import com.example.demo.repository.InvalidTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class schedulingConfig {
    @Autowired
    InvalidTokenRepository invalidTokenRepository;
    @Scheduled(cron = "0 0 */12 * * *")
    public void runAtFixedTime() {
        invalidTokenRepository.deleteAll();
    }
}
