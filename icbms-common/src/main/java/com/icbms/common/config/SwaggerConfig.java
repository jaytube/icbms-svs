package com.icbms.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by chenhai on 2017/10/12.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
                .select()  // 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.basePackage("com.icbms.web")) // 对该包下的api进行监控
                .paths(PathSelectors.any()) // 对该包下的所有路径进行监控
                .build();
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("智能空开管理系统")// 设置文档的标题
				.description("目前只提供App端Api接口查询")// 设置文档的描述->1.Overview
				.version("1.0.0")// 设置文档的版本信息-> 1.1 Version information
				.termsOfServiceUrl("www.icbms.com")// 设置文档的License信息->1.3 License information
				.build();
	}
}
