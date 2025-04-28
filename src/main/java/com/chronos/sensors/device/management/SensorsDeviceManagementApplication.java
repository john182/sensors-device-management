package com.chronos.sensors.device.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SensorsDeviceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorsDeviceManagementApplication.class, args);
    }

}
