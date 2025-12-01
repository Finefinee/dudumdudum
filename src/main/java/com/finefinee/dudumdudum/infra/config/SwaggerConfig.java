package com.finefinee.dudumdudum.infra.config;

import com.finefinee.dudumdudum.infra.config.security.CustomUserDetails;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Configuration
public class SwaggerConfig {

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthenticationPrincipal.class);
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(CustomUserDetails.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dudumdudum API")
                        .description("Dudumdudum Sleepover Management Service API")
                        .version("v1.0.0"));
    }
}
