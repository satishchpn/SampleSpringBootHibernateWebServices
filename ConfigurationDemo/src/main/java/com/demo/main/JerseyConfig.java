package com.demo.main;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.demo.endpoint.StudentEndpoint;

@Configuration
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		init();
	}

	private void init() {
		register(StudentEndpoint.class);
	}
}

