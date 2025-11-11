package com.programacion3.Sys_pos.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.programacion3.Sys_pos.dto.api_response.ApiResponse;
import com.programacion3.Sys_pos.dto.api_response.CreateRolDto;
import com.programacion3.Sys_pos.dto.api_response.RoleApiResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RolService {
    private final RestTemplate restTemplate;

    @Value("${api.nest.base-url:http://localhost:3000/api}")
    private String apiBaseUrl;

    public RolService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Método para crear un nuevo rol consumiendo la API Nest
     */
    public ApiResponse crearRol(CreateRolDto createRolDto) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                    .path("/rol")
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<CreateRolDto> requestEntity = new HttpEntity<>(createRolDto, headers);

            log.info("Enviando solicitud para crear rol: {}", createRolDto.getRol());

            // Usar NestApiResponse para la respuesta
            ResponseEntity<RoleApiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    RoleApiResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Rol creado exitosamente: {}", response.getBody().getMessage());
                return response.getBody().toGenericResponse();
            } else {
                log.error("Error en la respuesta de la API: {}", response.getStatusCode());
                return crearRespuestaError("Error al crear el rol: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error al consumir la API de roles: {}", e.getMessage());
            return crearRespuestaError("Error de conexión: " + e.getMessage());
        }
    }

    /**
     * Método para obtener todos los roles
     */
    public ApiResponse obtenerTodosLosRoles() {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                    .path("/rol")
                    .toUriString();

            // Cambiar a NestApiResponse
            ResponseEntity<RoleApiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    RoleApiResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Convertir la respuesta específica a genérica
                return response.getBody().toGenericResponse();
            } else {
                return crearRespuestaError("Error al obtener roles: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error al obtener roles: {}", e.getMessage());
            return crearRespuestaError("Error de conexión: " + e.getMessage());
        }
    }

    /**
     * Método para obtener un rol por ID
     */
    public ApiResponse obtenerRolPorId(Long id) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                    .path("/rol/" + id)
                    .toUriString();

            ResponseEntity<RoleApiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    RoleApiResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().toGenericResponse();
            } else {
                return crearRespuestaError("Error al obtener el rol: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error al obtener rol: {}", e.getMessage());
            return crearRespuestaError("Error de conexión: " + e.getMessage());
        }
    }

    private ApiResponse crearRespuestaError(String mensaje) {
        ApiResponse respuesta = new ApiResponse();
        respuesta.setOk(false);
        respuesta.setMessage(mensaje);
        return respuesta;
    }
}
