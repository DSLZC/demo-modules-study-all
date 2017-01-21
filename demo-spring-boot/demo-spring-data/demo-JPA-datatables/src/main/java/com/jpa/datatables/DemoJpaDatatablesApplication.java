package com.jpa.datatables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)// Enable the use of DataTablesRepository factory
public class DemoJpaDatatablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaDatatablesApplication.class, args);
	}
}
