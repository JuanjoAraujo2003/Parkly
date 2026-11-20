package com.parkingadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.parkingadmin.config.ParklyProperties;

@SpringBootApplication
@EnableConfigurationProperties(ParklyProperties.class)
public class ParkingAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParkingAdminApplication.class, args);
    }
}
