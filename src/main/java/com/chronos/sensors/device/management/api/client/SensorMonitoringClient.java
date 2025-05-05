package com.chronos.sensors.device.management.api.client;

import com.chronos.sensors.device.management.api.controller.responses.SensorMonitoringResponse;

import java.util.UUID;

public interface SensorMonitoringClient {
    void enableMonitoring(UUID sensorId);
    void disableMonitoring(UUID sensorId);
    SensorMonitoringResponse getDetail(UUID sensorId);
}
