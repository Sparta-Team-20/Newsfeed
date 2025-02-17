package com.example.newsfeed.user.controller;

import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.example.newsfeed.user.dto.response.UserFindOneResponseDto;
import com.example.newsfeed.user.dto.response.UserFindAllResponseDto;
import com.example.newsfeed.user.dto.response.UserSaveResponseDto;
import com.example.newsfeed.user.dto.response.UserUpdateResponseDto;
import com.example.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserSaveResponseDto> save(@RequestBody UserSaveRequestDto request) {
        UserSaveResponseDto response = userService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserFindAllResponseDto>> findAll() {
        List<UserFindAllResponseDto> responsePage = userService.findAll();
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserFindOneResponseDto> findOne(@PathVariable Long id) {
        UserFindOneResponseDto response = userService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@PostMapping("/users/{id}")
    public ResponseEntity<UserFindOneResponseDto> follow(@PathVariable Long id, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        UserFindOneResponseDto response = userService.follow(userId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

    @PostMapping("/users/{id}")
    public ResponseEntity<UserFindOneResponseDto> follow(@RequestParam("userId") Long id,
                                                         @RequestParam("targetId") Long targetId) {
        UserFindOneResponseDto response = userService.follow(id, targetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserUpdateResponseDto> update(@SessionAttribute(name = "LOGIN_USER") Long userId,
                                                        @RequestBody UserUpdateRequestDto request) {
        UserUpdateResponseDto response = userService.update(userId, request);
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
