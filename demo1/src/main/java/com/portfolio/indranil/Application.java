package com.portfolio.indranil;

import java.security.PublicKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
//Indranil12345-pwd
// -username
/*
	spring.thymeleaf.prefix=classpath:/templates/
	spring.thymeleaf.suffix=.html
	--These are optional
	but thymeleaf mvn dependency is needed
*/
	public String PORT=System.getenv("PORT");
	public static void main(String[] args) {
		
		
		SpringApplication.run(Application.class, args);
		System.out.println("hi");
	}

}
