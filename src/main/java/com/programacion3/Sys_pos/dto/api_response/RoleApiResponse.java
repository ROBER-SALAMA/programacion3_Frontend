package com.programacion3.Sys_pos.dto.api_response;

import java.util.List;

import lombok.Data;

@Data
public class RoleApiResponse {
    private boolean ok;
    private String message;
    private int status;
    private List<Object> objects; 

    public ApiResponse toGenericResponse() {
        ApiResponse response = new ApiResponse();
        response.setOk(this.ok);
        response.setMessage(this.message);
        response.setData(this.objects);
        return response;
    }
}
