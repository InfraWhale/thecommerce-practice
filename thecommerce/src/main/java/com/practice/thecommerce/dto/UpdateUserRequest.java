package com.practice.thecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "회원수정 Request")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUserRequest {

    @Schema(description="비밀번호", example="1Q2W3E4R%^&*")
    private String password;

    @Schema(description="닉네임", example="Butter2")
    private String nickName;

    @Schema(description="이름", example="Bob2")
    private String name;

    @Schema(description="핸드폰번호", example="0101111333")
    private String hp;

    @Schema(description="이메일주소", example="Butter361@gmail.com")
    private String emailAddress;
}
