package com.vajra.vacs.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocsConfig {
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("com.vajra").pathsToMatch("/**").build();
	}

	@Bean
	public OpenAPI vacsOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("VACS-Scorpius API").description("Vajra Access Control System API Documentation")
						.version("v0.0.1").license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("Scorpius Solutions")
						.url("https://scorpius.com.my/about"));
	}
}
