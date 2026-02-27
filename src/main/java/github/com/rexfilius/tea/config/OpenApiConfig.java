package github.com.rexfilius.tea.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Tea API", version = "v1.0", description = "API documentation for a blog app", contact = @Contact(name = "Ify Osakwe", email = "ifeakachukwuosakwe@gmail.com")), externalDocs = @ExternalDocumentation(description = "Tea (GitHub Repo)", url = "https://github.com/ify-osakwe/tea"))
public class OpenApiConfig {

}
