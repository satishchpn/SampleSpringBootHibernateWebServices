package com.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.demo")
@EntityScan("com.demo.entity")
public class Main extends SpringBootServletInitializer {
	public static void main(String[] args) {
		System.out.println("main started....");
        ApplicationContext ctx=SpringApplication.run(Main.class, args);
        System.out.println("Count : "+ctx.getBeanDefinitionCount());
        System.out.println("main finished....");
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }
}