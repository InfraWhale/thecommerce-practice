package com.practice.thecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description="회원가입 Response")
@Getter @Setter
@AllArgsConstructor
public class JoinUserResponse {
    @Schema(description="로그인 ID", example="Butter369")
    private String loginId;
}
