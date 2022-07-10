package org.bh;

import org.bh.filereader.model.LineConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SummaryParserApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SummaryParserApplication.class, args);
	}

	@Bean
	public static LineConfig buildLineConfig() {
		return new LineConfig();
	}
}
