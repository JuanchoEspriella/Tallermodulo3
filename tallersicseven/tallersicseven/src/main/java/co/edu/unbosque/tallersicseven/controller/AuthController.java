
package co.edu.unbosque.tallersicseven.controller;

import co.edu.unbosque.tallersicseven.dto.UserDTO;
import co.edu.unbosque.tallersicseven.model.User;
import co.edu.unbosque.tallersicseven.model.enums.Role;
import co.edu.unbosque.tallersicseven.security.JwtUtil;
import co.edu.unbosque.tallersicseven.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "API para autenticación de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserService userService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica al usuario y genera un token JWT."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Inicio de sesión exitoso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "token": "eyJhbGciOiJIUzI1NiJ9...",
                        "role": "USER"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas"
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.generateToken(userDetails);

            String role = null;

            if (userDetails instanceof User) {
                User user = (User) userDetails;

                if (user.getRole() != null) {
                    role = user.getRole().name();
                }
            }

            return ResponseEntity.ok(new AuthResponse(jwt, role));

        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Nombre de usuario o contraseña inválidos");
        }
    }

    @Operation(
        summary = "Registrar usuario",
        description = "Registra un nuevo usuario con rol USER."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario registrado exitosamente"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "El nombre de usuario ya existe"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al registrar el usuario"
        )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserDTO registerRequest) {

        if (userService.findUsernameAlreadyTaken(
                registerRequest.getUsername())) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("El nombre de usuario ya existe");
        }

        registerRequest.setRole(Role.USER);

        int result = userService.create(registerRequest);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado exitosamente");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error al registrar el usuario");
    }

    private static class AuthResponse {

        private final String token;
        private final String role;

        public AuthResponse(String token, String role) {
            this.token = token;
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public String getRole() {
            return role;
        }
    }
}
