package com.org.domainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DomainServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(DomainServiceApplication.class, args);
  }

  @Bean(name = "restTemplate")
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
