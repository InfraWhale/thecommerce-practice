package com.practice.thecommerce.controller;

import com.practice.thecommerce.dto.*;
import com.practice.thecommerce.entity.User;
import com.practice.thecommerce.repository.UserRepository;
import com.practice.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /*
    * 회원 가입
    * 요청 : JoinUserRequest {loginId(필수값), password(필수값), nickName, name, hp, emailAddress}
    * 응답 : id
    * 특이사항 : 필수값인 loginId, password는 반드시 입력되어야 함
    *           loginId는 중복될 수 없음
    * */
    @PostMapping("/api/user/join")
    public ResponseEntity joinUser(@RequestBody @Valid JoinUserRequest request) {

        User user = new User(request);
        Long id = userService.join(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    /*
     * 회원 목록 조회
     * 요청 : page(쿼리스트링), pageSize(쿼리스트링)
     * 응답 : Page<ListUserResponse>
     * 특이사항 : 가입일, 이름 순으로 정렬
     *           page(페이지 번호), pageSize(한 페이지에 표시되는 최대회원수)가 빈값일 경우,
     *           각각 0, 5로 설정
     * */
    @GetMapping("/api/user/list")
    public Page<ListUserResponse> listUser(@SortDefault.SortDefaults(
                                                       {@SortDefault(sort = "joinDate", direction = Sort.Direction.ASC)
                                                       , @SortDefault(sort = "name", direction = Sort.Direction.ASC)
                                                       }
                                               )
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "pageSize", defaultValue = "5") int pageSize
                                           ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> pages = userRepository.findAll(pageable);
        return pages.map(ListUserResponse::new);
    }

    /*
     * 회원 수정
     * 요청 : loginId
     * 응답 : UpdateUserResponse {loginId, password, nickName, name, hp, emailAddress, updateDate}
     * 특이사항 : 수정되지 않은 경우 "not updated"로 표시
     *           password 의 경우 수정된 경우 "updated"로 표시
     *          loginId를 조회해도 나오지 않는 경우 수정할 수 없음
     * */
    @PutMapping("/api/user/{loginId}")
    public UpdateUserResponse updateUserResponse(@PathVariable("loginId") String loginId,
                                                 @RequestBody UpdateUserRequest request) {
        return userService.update(loginId, request);
    }
}
