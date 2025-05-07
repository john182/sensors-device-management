package com.chronos.sensors.device.management.api.client.impl;

import com.chronos.sensors.device.management.api.client.RestClientFactory;
import com.chronos.sensors.device.management.api.client.SensorMonitoringClient;
import com.chronos.sensors.device.management.api.controller.responses.SensorMonitoringResponse;
import org.springframework.web.client.RestClient;

import java.util.UUID;

//@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }

    @Override
    public void enableMonitoring(UUID sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(UUID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public SensorMonitoringResponse getDetail(UUID sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringResponse.class);
    }
}
