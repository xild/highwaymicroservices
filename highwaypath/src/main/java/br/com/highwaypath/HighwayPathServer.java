package br.com.highwaypath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@EnableDiscoveryClient 
@Import(HighwayPathApplication.class)
public class HighwayPathServer {

	public static void main(String[] args) {
	    // Será configurado usando o highwaypath-services.yml
            System.setProperty("spring.config.name", "highwaypath-services");
	    SpringApplication.run(HighwayPathServer.class, args);
	}
}
