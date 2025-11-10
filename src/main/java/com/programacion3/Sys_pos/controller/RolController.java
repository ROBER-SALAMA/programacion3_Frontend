package com.programacion3.Sys_pos.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programacion3.Sys_pos.dto.api_response.ApiResponse;
import com.programacion3.Sys_pos.dto.api_response.CreateRolDto;
import com.programacion3.Sys_pos.services.RolService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RolController {
    private final RolService rolService;

    /**
     * Mostrar formulario para crear rol y lista de roles
     */
    @GetMapping
    public String mostrarFormularioRoles(Model model) {
        // Agregar DTO para el formulario
        if (!model.containsAttribute("createRolDto")) {
            model.addAttribute("createRolDto", new CreateRolDto());
        }

        // Obtener lista de roles existentes
        ApiResponse respuesta = rolService.obtenerTodosLosRoles();
        if (respuesta.isOk()) {
            model.addAttribute("roles", respuesta.getData());
        } else {
            model.addAttribute("error", respuesta.getMessage());
            model.addAttribute("roles", java.util.Collections.emptyList());
        }

        return "roles/lista";
    }

    /**
     * Procesar la creación de un nuevo rol
     */
    @PostMapping("/crear")
    public String crearRol(@Valid @ModelAttribute CreateRolDto createRolDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        // Validar errores de formulario
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createRolDto", result);
            redirectAttributes.addFlashAttribute("createRolDto", createRolDto);
            redirectAttributes.addFlashAttribute("error", "Por favor corrige los errores del formulario");
            return "redirect:/roles";
        }

        try {
            // Llamar al servicio para crear el rol
            ApiResponse respuesta = rolService.crearRol(createRolDto);

            if (respuesta.isOk()) {
                redirectAttributes.addFlashAttribute("success", respuesta.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("error", respuesta.getMessage());
            }

        } catch (Exception e) {
            log.error("Error al procesar la creación del rol: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error interno del servidor: " + e.getMessage());
        }

        return "redirect:/roles";
    }

    /**
     * Endpoint API para crear rol (para usar con AJAX/fetch)
     */
    @PostMapping("/role")
    @ResponseBody
    public ApiResponse crearRolApi(@Valid @RequestBody CreateRolDto createRolDto) {
        return rolService.crearRol(createRolDto);
    }
}
