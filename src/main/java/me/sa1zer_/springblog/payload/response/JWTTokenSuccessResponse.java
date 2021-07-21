package me.sa1zer_.springblog.payload.response;

import lombok.Data;

@Data
public class JWTTokenSuccessResponse {

    private boolean success;
    private String token;

    public JWTTokenSuccessResponse(boolean success, String message) {
        this.success = success;
        this.token = message;
    }
}
