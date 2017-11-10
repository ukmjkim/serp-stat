package com.serpstat.rest.actuator;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpointExtension;

@WebEndpointExtension(endpoint = CustomHealthEndpoint.class)
public class CustomHealthWebEndpointExtension {
    private final CustomHealthEndpoint delegate;

    public CustomHealthWebEndpointExtension(CustomHealthEndpoint delegate) {
        this.delegate = delegate;
    }

	@ReadOperation
	public WebEndpointResponse<CustomHealth> getHealth() {
		CustomHealth health = this.delegate.health();
		return new WebEndpointResponse<>(health, 200);
	}
}