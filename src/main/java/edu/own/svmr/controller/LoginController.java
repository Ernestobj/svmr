package edu.own.svmr.controller;

import edu.own.svmr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:江斌
 * @Description:
 * @Date: Created in 2021/8/19 21:38
 * @ModifiedBy:
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录，请登录!");
    }
}
