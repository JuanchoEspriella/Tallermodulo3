package co.edu.unbosque.tallersicseven.configuration;

<<<<<<< HEAD
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI (Swagger) para documentar la API REST. Esta clase define la información
 * general de la API, esquemas de seguridad, y componentes reutilizables para la documentación.
 */
@Configuration
public class OpenApiConfig {
  // Clase de configuración para la documentación OpenAPI

  /**
   * Define componentes reutilizables para la documentación OpenAPI. Incluye ejemplos de respuestas
   * comunes para mejorar la documentación.
   *
   * @return Configuración personalizada de OpenAPI
   */
  @Bean
  public OpenAPI customOpenAPI() {
    // Crear la descripción principal con HTML correctamente formateado
    String mainDescription =
        "<h2>Guía de TallerSICSeven</h2>"
            + "<p>Esta API ofrece autenticación JWT, gestión de usuarios y búsqueda y descarga de imágenes.</p>"
            + "<h3>Flujo básico de uso:</h3><ol>"
            + "<li>Registra un usuario usando <code>/auth/register</code>.</li>"
            + "<li>Inicia sesión usando <code>/auth/login</code> para obtener un token JWT.</li>"
            + "<li>En Swagger pulsa Authorize e introduce <code>Bearer tu_token_jwt</code>.</li>"
            + "<li>Utiliza los endpoints protegidos según el rol asignado.</li></ol>"
            + "<h3>Roles:</h3><ul>"
            + "<li><strong>USER</strong>: puede consultar los recursos protegidos.</li>"
            + "<li><strong>ADMIN</strong>: puede administrar usuarios.</li></ul>";

    // Crear la descripción del esquema de seguridad con HTML correctamente formateado
    String securityDescription =
        "Autenticación mediante JWT (JSON Web Token)."
            + "<p>Para autenticarte, sigue estos pasos:</p>"
            + "<ol>"
            + "    <li>Obtén un token JWT usando el endpoint <code>/auth/login</code></li>"
            + "    <li>Copia el token recibido en la respuesta</li>"
            + "    <li>Haz clic en el botón \"Authorize\" en la parte superior de esta página</li>"
            + "    <li>En el campo \"Value\", escribe: <code>Bearer tu_token_jwt</code></li>"
            + "    <li>Haz clic en \"Authorize\" y luego en \"Close\"</li>"
            + "</ol>"
            + "<p>Ahora podrás acceder a los endpoints protegidos.</p>";

    // Crear objeto Info con la descripción HTML
    io.swagger.v3.oas.models.info.Info info =
        new io.swagger.v3.oas.models.info.Info()
            .title("TallerSICSeven API")
            .version("1.0")
            .description(mainDescription)
            .contact(new io.swagger.v3.oas.models.info.Contact().name("Equipo de Desarrollo"));

    // Crear el esquema de seguridad
    io.swagger.v3.oas.models.security.SecurityScheme securityScheme =
        new io.swagger.v3.oas.models.security.SecurityScheme()
            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description(securityDescription);

    return new OpenAPI()
        .info(info)
        .components(
            new Components()
                .addSecuritySchemes("bearerAuth", securityScheme)
                .addResponses(
                    "UnauthorizedError",
                    new ApiResponse()
                        .description("No autenticado - Token JWT inválido o expirado")
                        .content(
                            new Content()
                                .addMediaType(
                                    "application/json",
                                    new MediaType()
                                        .addExamples(
                                            "error",
                                            new Example()
                                                .value(
                                                    "{\"error\": \"No autorizado\", \"mensaje\":"
                                                        + " \"Token inválido o expirado\"}")))))
                .addResponses(
                    "ForbiddenError",
                    new ApiResponse()
                        .description("Acceso prohibido - No tienes permisos suficientes")
                        .content(
                            new Content()
                                .addMediaType(
                                    "application/json",
                                    new MediaType()
                                        .addExamples(
                                            "error",
                                            new Example()
                                                .value(
                                                    "{\"error\": \"Acceso prohibido\", \"mensaje\":"
                                                        + " \"No tienes permisos para esta"
                                                        + " operación\"}")))))
                .addResponses(
                    "NotFoundError",
                    new ApiResponse()
                        .description("Recurso no encontrado")
                        .content(
                            new Content()
                                .addMediaType(
                                    "application/json",
                                    new MediaType()
                                        .addExamples(
                                            "error",
                                            new Example()
                                                .value(
                                                    "{\"error\": \"No encontrado\", \"mensaje\":"
                                                        + " \"El recurso solicitado no"
                                                        + " existe\"}"))))));
  }
}
=======
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Spring First App API",
        version = "1.0",
        description = "API for Spring First App with JWT Authentication"
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {
    // Configuration class for OpenAPI documentation
}


>>>>>>> f69b7ad (Configure OpenAPI for API documentation and add random Pokemon service)
