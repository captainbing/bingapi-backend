package com.abing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author CaptainBing
 * @Date 2023/10/6 21:01
 * @Description
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile[] file){
        return "nihao1";
    }

    @PostMapping("/upload1")
    public String uploadFile1(@RequestPart MultipartFile file){
        return "nihao1";
    }

}
