package com.example.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.catalina.Valve;
import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class TomcatStuckThreadDetectionConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{
	@Value("${server.tomcat.stuck-thread-detection.threshold:600}")
	
	private int threadshold;
	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		StuckThreadDetectionValve stuckThreadDetectionValve = new StuckThreadDetectionValve();
		stuckThreadDetectionValve.setThreshold(threadshold);
		stuckThreadDetectionValve.setThrowOnFailure(true);
		((TomcatServletWebServerFactory) factory).addContextValves(stuckThreadDetectionValve);
	}
	
}