package org.example.controller;

import org.example.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：
 * User: lmp
 * Date: 2023-07-30
 * Time: 17:15(李明浦)
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/1")
    public Object test1(){
        return testMapper.query(1);
    }
}
