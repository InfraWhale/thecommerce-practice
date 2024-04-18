package com.practice.thecommerce.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JoinUserRequest {
    private String loginId;

    private String password;

    private String nickName;

    private String name;

    private String hp;

    private String emailAddress;
}
