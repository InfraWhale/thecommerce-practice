package com.practice.thecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "회원수정 Response")
@Getter @Setter
@AllArgsConstructor
public class UpdateUserResponse {
    private String loginId;

    @Schema(description="비밀번호", example="updated")
    private String password = "not updated";

    @Schema(description="닉네임", example="Butter2")
    private String nickName = "not updated";

    @Schema(description="이름", example="Bob2")
    private String name = "not updated";

    @Schema(description="핸드폰번호", example="0101111333")
    private String hp = "not updated";

    @Schema(description="이메일주소", example="Butter361@gmail.com")
    private String emailAddress = "not updated";

    @Schema(description="마지막 수정일", example="2024-04-19T03:48:26.597Z")
    private LocalDateTime updateDate;

    public UpdateUserResponse(String loginId) {
        this.loginId = loginId;
    }
}
