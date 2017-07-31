package com.dslcode.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoJpaElasticsearchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaElasticsearchApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.debug("---------------------------启动-------------------------");
	}
}
