package edu.own.svmr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:江斌
 * @Description:
 * @Date: Created in 2021/8/19 20:05
 * @ModifiedBy:
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello jiangbin!!";
    }
}
