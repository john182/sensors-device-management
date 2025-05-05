package com.chronos.sensors.device.management.api.controller;

import com.chronos.sensors.device.management.api.client.SensorMonitoringClient;
import com.chronos.sensors.device.management.api.controller.requests.SensorRequest;
import com.chronos.sensors.device.management.api.controller.responses.SensorDetailResponse;
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
    @Autowired
    private SensorMonitoringClient sensorMonitoringClient;


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
    @GetMapping("{sensorId}/detail")
    public SensorDetailResponse getOneWithDetail(@PathVariable UUID sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var monitoringOuput = sensorMonitoringClient.getDetail(sensorId);
        var sensorOutput = convertToModel(sensor);

        return SensorDetailResponse.builder()
                .monitoring(monitoringOuput)
                .sensor(sensorOutput)
                .build();
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


    @PutMapping("/{sensorId}")
    public SensorResponse update(@PathVariable UUID sensorId,
                               @RequestBody SensorRequest input) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        sensor.setName(input.getName());
        sensor.setLocation(input.getLocation());
        sensor.setIp(input.getIp());
        sensor.setModel(input.getModel());
        sensor.setProtocol(input.getProtocol());

        sensor = sensorRepository.save(sensor);

        return convertToModel(sensor);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable UUID sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(true);
        sensorRepository.save(sensor);

        sensorMonitoringClient.enableMonitoring(sensorId);
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable UUID sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(false);
        sensorRepository.save(sensor);

        sensorMonitoringClient.disableMonitoring(sensorId);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensorRepository.delete(sensor);

        sensorMonitoringClient.disableMonitoring(sensorId);
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
