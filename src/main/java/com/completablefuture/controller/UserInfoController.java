package com.completablefuture.controller;

import com.completablefuture.entity.UserInfo;
import com.completablefuture.exception.UserInfoNotFoundException;
import com.completablefuture.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/users")

public class UserInfoController {
    private UserInfoService userInfoService;
    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping()
    public List<UserInfo> create(@RequestParam(value = "user-data") MultipartFile files) throws Exception {
        CompletableFuture<List<UserInfo>> future= userInfoService.saveUserInfo(files);
        return future.get();
    }
    @GetMapping()
    public List<UserInfo> allUsers() throws ExecutionException, InterruptedException, UserInfoNotFoundException {
        CompletableFuture<List<UserInfo>> future= userInfoService.findAllUsers();
        List<UserInfo> userInfos = future.get();
        if (userInfos == null || userInfos.size()==0)
            throw new UserInfoNotFoundException("No user foundException");
        return userInfos;
    }
}
