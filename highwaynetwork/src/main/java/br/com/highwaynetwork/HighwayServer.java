package br.com.highwaynetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@EnableDiscoveryClient 
@Import(HighwayApplication.class)
public class HighwayServer {

	public static void main(String[] args) {
		 // Será configurado usando o highway-services.yml
        System.setProperty("spring.config.name", "highway-services");
		SpringApplication.run(HighwayServer.class, args);
	}
}