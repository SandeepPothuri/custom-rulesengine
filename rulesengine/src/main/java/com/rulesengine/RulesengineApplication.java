package com.rulesengine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.rulesengine", lazyInit = true)
public class RulesengineApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(RulesengineApplication.class).run(args);
	}

}
