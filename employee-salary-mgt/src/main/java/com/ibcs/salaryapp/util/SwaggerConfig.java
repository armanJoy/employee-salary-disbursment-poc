package com.ibcs.salaryapp.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASIC_AUTH = "basicAuth";
    private static final String BEARER_AUTH = "Bearer";

    @Value("${app.version}")
    private String APP_VERSION;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.ibcs.salaryapp")).paths(PathSelectors.any()).build().apiInfo(apiInfo()).securitySchemes(securitySchemes()).securityContexts(new ArrayList<>(Arrays.asList(securityContext())));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Vizzhy REST API", "Vizzhy REST API", APP_VERSION, "Terms of service", new Contact("", "", ""), "License of API", "API license URL", Collections.emptyList());
    }

    private List<SecurityScheme> securitySchemes() {
        return new ArrayList<>(Arrays.asList(new BasicAuth(BASIC_AUTH), new ApiKey(BEARER_AUTH, "Authorization", "header")));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(new ArrayList<>(Arrays.asList(basicAuthReference(), bearerAuthReference()))).forPaths(PathSelectors.ant("/**")).build();
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
    }

    private SecurityReference bearerAuthReference() {
        return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
    }

}
