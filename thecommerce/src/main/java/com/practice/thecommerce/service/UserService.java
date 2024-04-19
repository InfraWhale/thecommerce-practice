package com.practice.thecommerce.service;

import com.practice.thecommerce.dto.UpdateUserRequest;
import com.practice.thecommerce.dto.UpdateUserResponse;
import com.practice.thecommerce.entity.User;
import com.practice.thecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.util.StringUtils.hasText;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getLoginId();
    }

    public UpdateUserResponse update(String loginId, UpdateUserRequest request) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalStateException("업데이트 할 회원 정보가 없습니다."));

        UpdateUserResponse response = new UpdateUserResponse(loginId);

        if(hasText(request.getPassword())) {
            user.setPassword(request.getPassword());
            response.setPassword("updated");
        }
        if(hasText(request.getName())) {
            user.setName(request.getName());
            response.setName(request.getName());
        }
        if(hasText(request.getNickName())) {
            user.setNickName(request.getNickName());
            response.setNickName(request.getNickName());
        }
        if(hasText(request.getHp())) {
            user.setHp(request.getHp());
            response.setHp(request.getHp());
        }
        if(hasText(request.getEmailAddress())) {
            user.setEmailAddress(request.getEmailAddress());
            response.setEmailAddress(request.getEmailAddress());
        }
        user.setUpdateDate(now());
        response.setUpdateDate(now());

        return response;
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findAllByLoginId(user.getLoginId());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }
}
