package com.practice.thecommerce.entity;

import com.practice.thecommerce.dto.JoinUserRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String password;

    private String nickName;

    private String name;

    private String hp;

    private String emailAddress;

    private LocalDateTime joinDate;

    private LocalDateTime updateDate;

    public User(JoinUserRequest request) {
        this.loginId = request.getLoginId();
        this.password = request.getPassword();
        this.nickName = request.getNickName();
        this.name = request.getName();
        this.hp = request.getHp();
        this.emailAddress = request.getEmailAddress();
        this.joinDate = now();
        this.updateDate = now();
    }
}
