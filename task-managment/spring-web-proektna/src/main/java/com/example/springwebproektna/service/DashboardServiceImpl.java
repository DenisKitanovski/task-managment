package com.example.springwebproektna.service;

import com.example.springwebproektna.model.Dashboard;
import com.example.springwebproektna.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DashboardServiceImpl implements DashboardService {

    private DashboardRepository dashboardRepository;

    @Autowired
    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public Optional<Dashboard> findById(String id) {
        return dashboardRepository.findById(id);
    }
}
