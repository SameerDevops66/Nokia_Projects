package com.nokia.reactivejokes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2).enable(true)
				.apiInfo(new ApiInfoBuilder().build()).select()
				.apis(RequestHandlerSelectors.basePackage("com.nokia.reactivejokes.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
		}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Jokes API")
				.description("Provide jokes on demand")
				.contact(new Contact("Team", "nokia.com", "dev@gmail.com"))
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("0.0.1")
				.build();
	}

}
