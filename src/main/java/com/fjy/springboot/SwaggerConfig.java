package com.fjy.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Servlet测试
 * @author Tomato
 * http://localhost:3838/shabi/swagger-ui.html 测试Servlet，看APP端、硬件端数据交互
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createAllRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("all").select()
				.apis(RequestHandlerSelectors.basePackage("com.fjy"))
				.paths(Predicates.or(PathSelectors.regex("/.*"))).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("smartMonitorSystem API").version("1").build();
	}

}