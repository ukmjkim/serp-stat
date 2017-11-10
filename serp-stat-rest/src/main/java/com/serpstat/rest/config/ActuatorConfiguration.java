package com.serpstat.rest.config;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.serpstat.rest.actuator.CustomHealthEndpoint;
import com.serpstat.rest.actuator.CustomHealthWebEndpointExtension;

@Configuration
public class ActuatorConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public CustomHealthEndpoint myHealthEndpoint() {
        return new CustomHealthEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    @ConditionalOnBean({CustomHealthEndpoint.class})
    public CustomHealthWebEndpointExtension myHealthWebEndpointExtension(
    		CustomHealthEndpoint delegate) {
        return new CustomHealthWebEndpointExtension(delegate);
    }
}