package com.practice.thecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Schema(description="회원가입 Request")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JoinUserRequest {
    @Schema(description="로그인 ID", example="Butter369")
    @NotEmpty
    private String loginId;

    @Schema(description="비밀번호", example="1Q2W3E4R")
    @NotEmpty
    private String password;

    @Schema(description="닉네임", example="Butter")
    private String nickName;

    @Schema(description="이름", example="Bob")
    private String name;

    @Schema(description="핸드폰번호", example="01011112222")
    private String hp;

    @Schema(description="이메일주소", example="Butter369@gmail.com")
    private String emailAddress;
}
