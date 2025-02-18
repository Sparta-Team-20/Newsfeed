package com.example.newsfeed.user.controller;

import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.example.newsfeed.user.dto.response.UserFindAllResponseDto;
import com.example.newsfeed.user.dto.response.UserFindOneResponseDto;
import com.example.newsfeed.user.dto.response.UserSaveResponseDto;
import com.example.newsfeed.user.dto.response.UserUpdateResponseDto;
import com.example.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/register")
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
        UserFindOneResponseDto response = userService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@PostMapping("/users/{id}")
    public ResponseEntity<UserFindOneResponseDto> follow(@PathVariable Long id, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        UserFindOneResponseDto response = userService.follow(userId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

    @PostMapping("/users/me/{id}")
    public ResponseEntity<UserFindOneResponseDto> follow(@PathVariable Long id,
                                                         HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        UserFindOneResponseDto response = userService.follow(userId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserUpdateResponseDto> update(HttpServletRequest request,
                                                        @RequestBody UserUpdateRequestDto updateRequest) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        UserUpdateResponseDto response = userService.update(userId, updateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/me/addImage")
    public ResponseEntity<UserUpdateResponseDto> addImage(HttpServletRequest request,
                                                          @RequestBody String requestDto) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        UserUpdateResponseDto response = userService.addImage(userId, requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/me/deleteImage/{id}")
    public ResponseEntity<UserUpdateResponseDto> deleteImage(HttpServletRequest request,
                                                             @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        UserUpdateResponseDto response = userService.deleteImage(userId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> delete(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
