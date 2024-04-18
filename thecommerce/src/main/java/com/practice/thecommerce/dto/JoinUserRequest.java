package com.practice.thecommerce.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JoinUserRequest {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    private String nickName;

    private String name;

    private String hp;

    private String emailAddress;
}
