package com.serpstat.rest.actuator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id = "customhealth")
public class CustomHealthEndpoint {

    @ReadOperation
    public CustomHealth health() {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("status", "okay");
        CustomHealth health = new CustomHealth();
        health.setDetails(details);

        return health;
    }
}