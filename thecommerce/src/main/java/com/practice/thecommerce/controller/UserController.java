package com.practice.thecommerce.controller;

import com.practice.thecommerce.dto.*;
import com.practice.thecommerce.entity.User;
import com.practice.thecommerce.repository.UserRepository;
import com.practice.thecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User", description = "User 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    
    @Operation(summary = "회원 가입",
            description = "회원을 생성합니다. " + "<br>" +
                    "필수값인 loginId, password는 반드시 입력되어야 합니다. " + "<br>" +
                    "loginId는 중복될 수 없습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = JoinUserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "필수정보가 누락되었습니다.",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "회원 가입 시 로그인 id 중복이 발생했습니다.",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/api/user/join")
    public ResponseEntity joinUser(@RequestBody @Valid JoinUserRequest request) {

        User user = new User(request);
        String loginId = userService.join(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginId);
    }

    @Operation(summary = "회원 목록 조회",
            description = "회원 목록을 페이징 할 수 있도록 조회합니다. " + "<br>" +
                    "가입일, 이름 순으로 정렬 합니다. " + "<br>" +
                    "page(페이지 번호), pageSize(한 페이지에 표시되는 최대회원수)가 빈값일 경우, 각각 0, 5로 설정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원조회에 성공하였습니다.")
    })
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

    @Operation(summary = "회원 수정",
            description = "회원정보를 수정합니다. " + "<br>" +
                    "수정되지 않은 경우 \"not updated\"로 표시합니다." + "<br>" +
                    "password가 수정된 경우 \"updated\"로 표시합니다." + "<br>" +
                    "loginId를 조회해도 나오지 않는 경우 수정할 수 없습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "loginId를 조회해도 나오지 않는 경우 수정할 수 없습니다.",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/api/user/{loginId}")
    public UpdateUserResponse updateUserResponse(@PathVariable("loginId") String loginId,
                                                 @RequestBody UpdateUserRequest request) {
        return userService.update(loginId, request);
    }
}
