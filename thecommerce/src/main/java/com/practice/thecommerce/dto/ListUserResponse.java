package com.practice.thecommerce.dto;

import com.practice.thecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class ListUserResponse {
    private String loginId;

    private String nickName;

    private String name;

    private String hp;

    private String emailAddress;

    private LocalDateTime joinDate;

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
