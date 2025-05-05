package com.chronos.sensors.device.management.api.controller.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDetailResponse {
    private SensorResponse sensor;
    private SensorMonitoringResponse monitoring;
}
