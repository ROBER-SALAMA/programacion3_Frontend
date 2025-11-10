package com.programacion3.Sys_pos.dto.api_response;

import lombok.Data;

@Data

public class ApiResponse {
    private boolean ok;
    private String message;
    private Object data;
}
