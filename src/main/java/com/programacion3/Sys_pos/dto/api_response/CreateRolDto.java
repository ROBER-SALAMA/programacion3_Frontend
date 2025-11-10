package com.programacion3.Sys_pos.dto.api_response;

import lombok.Data;
// import jakarta.validation.constraints.NotEmpty;

@Data

public class CreateRolDto {
    // @NotEmpty(message = "El rol no puede estar vacío")
    private String rol;
}
