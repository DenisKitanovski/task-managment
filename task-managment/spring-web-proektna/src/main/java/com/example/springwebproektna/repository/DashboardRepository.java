package com.example.springwebproektna.repository;

import com.example.springwebproektna.model.Dashboard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DashboardRepository extends MongoRepository<Dashboard, String> {
    Dashboard save(Dashboard dashboard);

    Optional<Dashboard> findById(String id);
}
