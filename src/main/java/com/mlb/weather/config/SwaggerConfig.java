package com.mlb.weather.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mlb.weather.controller"))
				.paths(PathSelectors.regex("/.*"))
				.build().apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Spring Boot REST API")
				.description("MLB weather REST API")
				.contact(new Contact("nick nikandish", "https://github.com/nnika/", "nick.nikandish@gmail.com"))
				.license("Apache 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
				.version("1.0.0")
				.build();
	}




	//this is a change
}
