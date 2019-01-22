package com.isaiahlee224.webservice.web;

import com.isaiahlee224.webservice.domain.common.CommonResponse;
import com.isaiahlee224.webservice.dto.PostsSaveRequestDto;
import com.isaiahlee224.webservice.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsService postsService;
    private Environment env;

    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }

    @PostMapping("/posts")
    public CommonResponse savePosts(@RequestBody PostsSaveRequestDto dto){
        postsService.save(dto);

        return CommonResponse.builder()
                .result("OK")
                .message("msg")
                .build();
    }

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .filter(e -> "set1".equals(e) || "set2".equals(e) || "local".equals(e))
                        .findFirst()
                        .orElse("");
    }
}
