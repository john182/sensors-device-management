package com.chronos.sensors.device.management.api.controller.requests;

import lombok.Data;

@Data
public class SensorRequest {
    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
}
