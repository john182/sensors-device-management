package com.chronos.sensors.device.management.domain.repository;

import com.chronos.sensors.device.management.domain.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {
}
