package com.bwgjoseph.springjavafxclient.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "feathers")
public class FeathersConfig {
	private String webSocketUri;
}
