package com.example.myblog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.example.myblog.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * 验证码的控制器
 */
@RestController
public class CaptchaController {

    @Value("${imagepath}")
    private String imagepath;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/getcaptcha")
    public AjaxResult getCaptcha(){
        // 1.生成验证码到本地
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(128, 50);
        String uuid = UUID.randomUUID().toString().replace("-","");
        // 图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write(imagepath+uuid+".png");
        // url 地址
        String url = "/image/"+uuid+".png";
        // 将验证码存储到 redis
        redisTemplate.opsForValue().set(uuid,lineCaptcha.getCode());
        HashMap<String,String> result = new HashMap<>();
        result.put("codeurl",url);
        result.put("codekey",uuid);
        return AjaxResult.succ(result);
    }

}
