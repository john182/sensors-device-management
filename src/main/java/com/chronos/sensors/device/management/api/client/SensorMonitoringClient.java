package com.chronos.sensors.device.management.api.client;

import com.chronos.sensors.device.management.api.controller.responses.SensorMonitoringResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.UUID;

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {
    @PutExchange("/enable")
    void enableMonitoring(@PathVariable UUID sensorId);
    @DeleteExchange("/enable")
    void disableMonitoring(@PathVariable UUID sensorId);
    @GetExchange
    SensorMonitoringResponse getDetail(@PathVariable UUID sensorId);
}
