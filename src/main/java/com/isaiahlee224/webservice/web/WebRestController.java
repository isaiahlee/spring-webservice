package com.isaiahlee224.webservice.web;

import com.isaiahlee224.webservice.domain.common.CommonResponse;
import com.isaiahlee224.webservice.domain.posts.PostsRepository;
import com.isaiahlee224.webservice.dto.PostsSaveRequestDto;
import com.isaiahlee224.webservice.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsRepository postsRepository;
    private PostsService postsService;

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
}
