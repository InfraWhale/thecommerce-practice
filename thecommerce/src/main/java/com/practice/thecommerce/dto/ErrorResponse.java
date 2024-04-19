package com.practice.thecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Schema(description="에러 상황 dto")
@Getter
public class ErrorResponse {
    @Schema(description="에러메시지", example="errorMessage")
    private String errorMessage;

    @Schema(description="에러코드", example="errorCode")
    private String errorCode;
}
