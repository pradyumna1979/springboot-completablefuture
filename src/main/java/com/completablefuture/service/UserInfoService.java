package com.completablefuture.service;

import com.completablefuture.entity.UserInfo;
import com.completablefuture.exception.FileParseException;
import com.completablefuture.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserInfoService {
    private UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
    @Async
    public CompletableFuture<List<UserInfo>> saveUserInfo(MultipartFile multipartFile) throws Exception {
        long startTime = System.currentTimeMillis();
        List<UserInfo> userInfos = parseCSV(multipartFile);
        log.info("saving list of users of size {}", userInfos.size()+" " +Thread.currentThread().getName());
        List<UserInfo> infos=userInfoRepository.saveAll(userInfos);
        long endtime = System.currentTimeMillis();
        log.info("Time taken to save: {}", endtime - startTime);
        return CompletableFuture.completedFuture(infos);
    }
    @Async
    public CompletableFuture<List<UserInfo>> findAllUsers(){
        long startTime = System.currentTimeMillis();
        List<UserInfo> userInfos= userInfoRepository.findAll();
        long endTime = System.currentTimeMillis();
        log.info("Time required to fetch users {} ",endTime-startTime);
        return CompletableFuture.completedFuture(userInfos);
    }
    private List<UserInfo> parseCSV(MultipartFile multipartFile) throws Exception {
        Function<String[],UserInfo> userInfoFunction = strings -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(strings[1]);
            userInfo.setName(strings[2]);
            userInfo.setPassword(strings[3]);
            userInfo.setRoles(strings[4]);
            return userInfo;
        };
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
                return br.lines().skip(1).map(s -> s.split(","))
                        .map(userInfoFunction)
                        .collect(Collectors.toList());
            }
        } catch (final Exception exception) {
            log.error("failed to parse csvFile {}", exception);
            throw new Exception("Failed to parse csvfile {}", exception);
        }
    }
}
