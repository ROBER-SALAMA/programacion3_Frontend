package com.programacion3.Sys_pos.dto.api_response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class CreateRolDto {
    @NotEmpty(message = "El rol no puede estar vacío")
    private String rol;
}
