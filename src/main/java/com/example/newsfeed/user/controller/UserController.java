package com.example.newsfeed.user.controller;

import com.example.newsfeed.user.dto.request.UserSaveRequest;
import com.example.newsfeed.user.dto.request.UserUpdateRequest;
import com.example.newsfeed.user.dto.response.UserFindOneResponse;
import com.example.newsfeed.user.dto.response.UserFindAllResponse;
import com.example.newsfeed.user.dto.response.UserSaveResponse;
import com.example.newsfeed.user.dto.response.UserUpdateResponse;
import com.example.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserSaveResponse> save(@RequestBody UserSaveRequest request) {
        UserSaveResponse response = userService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserFindAllResponse>> findAll(@PageableDefault Pageable pageable) {
        Page<UserFindAllResponse> responsePage = userService.findAll(pageable);
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserFindOneResponse> findById(@PathVariable Long id) {
        UserFindOneResponse response = userService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<UserFindOneResponse> follow(@PathVariable Long id, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        UserFindOneResponse response = userService.follow(userId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserUpdateResponse> update(@SessionAttribute(name = "LOGIN_USER") Long userId,
                                                     @RequestBody UserUpdateRequest request) {
        UserUpdateResponse response = userService.update(userId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/users/me")
    public ResponseEntity<Void> delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("LOGIN_USER");
        userService.delete(userId);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
