package com.codetutr.controller.Security;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import({ org.springdoc.core.SpringDocConfiguration.class, SpringDocWebMvcConfiguration.class,
    SwaggerConfig.class, org.springdoc.core.SwaggerUiConfigProperties.class,
    org.springdoc.core.SwaggerUiOAuthProperties.class,
    org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class, org.springdoc.core.SpringDocConfigProperties.class })
public class MySwaggerConfig {

	@Bean
	public GroupedOpenApi storeOpenApi() {
		String paths[] = {"/store/**"};
		return GroupedOpenApi.builder().group("stores").pathsToMatch(paths)
				.build();
	}
	@Bean
	public GroupedOpenApi storeOpenApi1() {
		String paths[] = {"/api/**"};
		return GroupedOpenApi.builder().group("go-lang").pathsToMatch(paths)
				.build();
	}
	
}
