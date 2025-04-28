package com.chronos.sensors.device.management.api.controller;

import com.chronos.sensors.device.management.api.controller.requests.SensorRequest;
import com.chronos.sensors.device.management.api.controller.responses.SensorResponse;
import com.chronos.sensors.device.management.domain.model.Sensor;
import com.chronos.sensors.device.management.domain.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping
    public Page<SensorResponse> search(@PageableDefault Pageable pageable) {
        var sensors = sensorRepository.findAll(pageable);
        return sensors.map(this::convertToModel);
    }


    @GetMapping("{sensorId}")
    public SensorResponse getOne(@PathVariable UUID sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return convertToModel(sensor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorResponse create(@RequestBody SensorRequest input) {
        var sensor = Sensor.builder()
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();
        sensorRepository.saveAndFlush(sensor);

        return convertToModel(sensor);
    }

    private SensorResponse convertToModel(Sensor sensor) {
        return SensorResponse.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }
}
