package com.wyki.idsauth.configs;


//import com.google.common.collect.Lists;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Swagger 2 configuration.
 *
 * @Author munialo.roy @ekenya.co.ke
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    /**
     * The constant AUTHORIZATION_HEADER.
     */
//    public static final String AUTHORIZATION_HEADER = "Authorization";
    /**
     * The constant DEFAULT_INCLUDE_PATTERN.
     */
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";


    /**
     * Mobile apis docket.
     *
     * @return the docket
     */
    @Bean
    public Docket mobileApis() {

//        List<SecurityScheme> schemeList = new ArrayList<>();
//        schemeList.add(new BasicAuth("basicAuth"));
//        schemeList.add(new BasicAuth("Bearer access_token"));
        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("mobile")
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.wyki.idsauth.controllers"))
                .paths(PathSelectors.any())

                .build().apiInfo(apiEndPointsInfo());
//                .securityContexts(Lists.newArrayList(securityContext()))
//                .securitySchemes(Lists.newArrayList(apiKey()));
    }

//    private ApiKey apiKey() {
//        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("STAFF APPLICATION RESTFUL APIS")
                .description("API Documentation for the authentication microservice")
//                .contact(new Contact("Munialo Roy Wati", "ekenya.co.ke", "munialo.roy@ekenya.co.ke"))
                .contact(new Contact("Wycliff G. Muriithi","","wycliff.muriithi@kra.go.ke"))
                .license("Apache 2.0")
               // .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
//                .build();
//    }
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Lists.newArrayList(
//                new SecurityReference("JWT", authorizationScopes));
//    }
}
