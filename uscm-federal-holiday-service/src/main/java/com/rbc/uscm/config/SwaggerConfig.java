package com.rbc.uscm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//import springfox.documentation.builders.PathSelectors;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {
	
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("USCM Payment")
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rbc.uscm"))
                .paths(regex("/api.*"))
                //.apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        		.title("Federal Holiday Service")
                .description("Federal Holiday Rest API")
                .version("2.0")
                .build();
    }


}
