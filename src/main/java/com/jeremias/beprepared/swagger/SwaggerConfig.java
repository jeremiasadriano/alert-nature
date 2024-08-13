package com.jeremias.beprepared.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                version = "1.0",
                title = "BePrepared",
                description = "An app that allows/ensures the security of its users",
                contact = @Contact(
                        name = "Jeremias Adriano",
                        email = "adrianojeremias1@gmail.com",
                        url = "https://github.com/jeremiasadriano"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/license/mit"
                )
        ),
        security = @SecurityRequirement(
                name = "Bearer Authentication"
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Put your token to be authenticated",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
