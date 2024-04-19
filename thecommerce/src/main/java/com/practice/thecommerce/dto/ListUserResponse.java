package com.practice.thecommerce.dto;

import com.practice.thecommerce.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description="회원조회 Response")
@Getter @Setter
@AllArgsConstructor
public class ListUserResponse {
    @Schema(description="로그인 ID", example="Butter369")
    private String loginId;

    @Schema(description="닉네임", example="Butter")
    private String nickName;

    @Schema(description="이름", example="Bob")
    private String name;

    @Schema(description="핸드폰번호", example="01011112222")
    private String hp;

    @Schema(description="이메일주소", example="Butter369@gmail.com")
    private String emailAddress;

    @Schema(description="회원가입일", example="2024-04-19T03:48:26.597Z")
    private LocalDateTime joinDate;

    @Schema(description="마지막 수정일", example="2024-04-19T03:48:26.597Z")
    private LocalDateTime updateDate;
    public ListUserResponse(User user) {
        this.loginId = user.getLoginId();
        this.nickName = user.getNickName();
        this.name = user.getName();
        this.hp = user.getHp();
        this.emailAddress = user.getEmailAddress();
        this.joinDate = user.getJoinDate();
        this.updateDate = user.getUpdateDate();
    }
}
