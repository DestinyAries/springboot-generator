package com.destiny.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"com.destiny.generator.*"})
public class SpringbootGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGeneratorApplication.class, args);
	}

}
