package com.feedbackservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class FeedbackserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackserviceApplication.class, args);
	}

	@Bean
	public OpenAPI openApiInformation() {
		Server localServer = new Server()
				.url("http://localhost:8086")
				.description("Localhost Server URL");

		Info info = new Info()
				.description("Feedback service related apis")
				.title("Feedback service api");

		return new OpenAPI().info(info).addServersItem(localServer);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
