package com.practice.thecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UpdateUserResponse {
    private String loginId;

    private String password = "not updated";

    private String nickName = "not updated";

    private String name = "not updated";

    private String hp = "not updated";

    private String emailAddress = "not updated";

    public UpdateUserResponse(String loginId) {
        this.loginId = loginId;
    }
}
