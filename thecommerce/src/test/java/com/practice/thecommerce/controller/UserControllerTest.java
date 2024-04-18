package com.practice.thecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.thecommerce.dto.JoinUserRequest;
import com.practice.thecommerce.dto.UpdateUserRequest;
import com.practice.thecommerce.entity.User;
import com.practice.thecommerce.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("회원 가입 성공")
    @Test
    public void joinUserSuccess() throws Exception {
        //given
        String url = "/api/user/join";
        String loginId = "id1";
        String password = "password1";
        String nickName = "nickName1";
        String name = "name1";
        String hp = "01011112222";
        String emailAddress = "email1@gmail.com";

        JoinUserRequest request = new JoinUserRequest(loginId, password, nickName, name, hp, emailAddress);

        final String requestBody = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        User user = userRepository.findByLoginId(loginId).get();
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getNickName()).isEqualTo(nickName);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getHp()).isEqualTo(hp);
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
    }

    @DisplayName("회원 가입 시 필수정보 누락 (로그인id, 비밀번호)")
    @Test
    public void joinUserFail1() throws Exception {
        //given
        String url = "/api/user/join";
        String loginId = "";
        String password = "";
        String nickName = "nickName1";
        String name = "name1";
        String hp = "01011112222";
        String emailAddress = "email1@gmail.com";

        JoinUserRequest request = new JoinUserRequest(loginId, password, nickName, name, hp, emailAddress);

        final String requestBody = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().is4xxClientError());
    }

    @DisplayName("회원 가입 시 로그인 id 중복 발생")
    @Test
    public void joinUserFail2() throws Exception {
        //given
        String url = "/api/user/join";
        String loginId = "user1";
        String password = "1234";
        String nickName = "nickName1";
        String name = "name1";
        String hp = "01011112222";
        String emailAddress = "email1@gmail.com";

        JoinUserRequest request = new JoinUserRequest(loginId, password, nickName, name, hp, emailAddress);
        final String requestBody = objectMapper.writeValueAsString(request);

        userRepository.save(new User(request));

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                )).hasCause(new IllegalStateException("이미 존재하는 아이디입니다."));
    }

    @DisplayName("회원 목록 조회 쿼리스트링 없이")
    @Test
    public void listUserSuccess() throws Exception {
        //given
        String url = "/api/user/list";
        for (int i = 0; i < 10; i++) {
            String loginId = "id" + i;
            String password = "password" + i;
            String nickName = "nickName" + i;
            String name = "name" + i;
            String hp = "01011112222" +i;
            String emailAddress = "email"+ i +"@gmail.com";
            userRepository.save(new User(new JoinUserRequest(loginId, password, nickName, name, hp, emailAddress)));
        }

        //when
        ResultActions result = mockMvc.perform(get(url));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @DisplayName("회원 목록 조회 쿼리스트링 써서")
    @Test
    public void listUserSuccess2() throws Exception {
        //given
        String url = "/api/user/list?page=1&pageSize=4";
        for (int i = 0; i < 10; i++) {
            String loginId = "id" + i;
            String password = "password" + i;
            String nickName = "nickName" + i;
            String name = "name" + i;
            String hp = "01011112222" +i;
            String emailAddress = "email"+ i +"@gmail.com";
            userRepository.save(new User(new JoinUserRequest(loginId, password, nickName, name, hp, emailAddress)));
        }

        //when
        ResultActions result = mockMvc.perform(get(url));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.pageable.pageSize").value(4))
                .andExpect(jsonPath("$.totalPages").value(3));
    }

    @DisplayName("회원 수정 성공")
    @Test
    public void updateUserSuccess() throws Exception {
        //given
        String url = "/api/user/{loginId}";
        String loginId = "id1";
        String password = "password1";
        String nickName = "nickName1";
        String name = "name1";
        String hp = "01011112222";
        String emailAddress = "email1@gmail.com";
        userRepository.save(new User(new JoinUserRequest(loginId, password, nickName, name, hp, emailAddress)));

        password = "password2";
        nickName = "nickName2";
        name = "name2";
        hp = "01011112223";
        emailAddress = "email2@gmail.com";

        UpdateUserRequest request = new UpdateUserRequest(password, nickName, name, hp, emailAddress);

        final String requestBody = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(put(url, loginId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("updated"))
                .andExpect(jsonPath("$.nickName").value(nickName))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.hp").value(hp))
                .andExpect(jsonPath("$.emailAddress").value(emailAddress));
    }

    @DisplayName("회원 수정 시 수정할 계정 없음")
    @Test
    public void updateUserFail() throws Exception {
        //given
        String url = "/api/user/{loginId}";
        String loginId = "id9";
        String password = "password9";
        String nickName = "nickName9";
        String name = "name9";
        String hp = "01011119999";
        String emailAddress = "email9@gmail.com";

        UpdateUserRequest request = new UpdateUserRequest(password, nickName, name, hp, emailAddress);

        final String requestBody = objectMapper.writeValueAsString(request);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> mockMvc.perform(put(url, loginId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
                )).hasCause(new IllegalStateException("업데이트 할 회원 정보가 없습니다."));
    }
}