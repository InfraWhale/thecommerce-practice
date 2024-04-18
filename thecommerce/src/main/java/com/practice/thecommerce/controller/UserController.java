package com.practice.thecommerce.controller;

import com.practice.thecommerce.dto.*;
import com.practice.thecommerce.entity.User;
import com.practice.thecommerce.repository.UserRepository;
import com.practice.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/api/user/join")
    public ResponseEntity joinUser(@RequestBody @Valid JoinUserRequest request) {

        User user = new User(request);
        Long id = userService.join(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/api/user/list")
    public Page<ListUserResponse> listUser(@PageableDefault(size = 5)
                                               @SortDefault.SortDefaults(
                                                       {@SortDefault(sort = "joinDate", direction = Sort.Direction.ASC)
                                                       , @SortDefault(sort = "name", direction = Sort.Direction.ASC)
                                                       }
                                               )
                                               Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page.map(ListUserResponse::new);
    }

    @PutMapping("/api/user/{loginId}")
    public UpdateUserResponse updateUserResponse(@PathVariable("loginId") String loginId,
                                                 @RequestBody UpdateUserRequest request) {
        return userService.update(loginId, request);
    }
}
