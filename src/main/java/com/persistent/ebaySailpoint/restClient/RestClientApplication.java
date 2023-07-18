package com.persistent.ebaySailpoint.restClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestClientApplication extends SpringBootServletInitializer {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(RestClientApplication.class);
//	}
	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);
	}

	@GetMapping(path = "/ping")
	public String Hello(){
		return "pong";
	}

}
