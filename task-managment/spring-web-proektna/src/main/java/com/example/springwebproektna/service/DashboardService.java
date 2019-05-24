package com.example.springwebproektna.service;

import com.example.springwebproektna.model.Dashboard;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface DashboardService {

    Optional<Dashboard> findById(String id);
}
