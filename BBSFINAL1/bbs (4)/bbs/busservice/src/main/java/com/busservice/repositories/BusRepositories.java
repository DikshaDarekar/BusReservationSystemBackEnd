package com.busservice.repositories;

import com.busservice.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepositories extends JpaRepository<Bus,Long> {
}
