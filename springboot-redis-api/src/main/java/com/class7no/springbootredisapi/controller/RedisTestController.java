package com.class7no.springbootredisapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huahua
 * api方式整合redis
 */
@RestController
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/save")
    public Object save(@RequestParam("msg") String msg){
        redisTemplate.boundValueOps("111").set(msg);
        return "SUCCESS";
    }
    @GetMapping("/findAll")
    public Object findAll(@RequestParam("key") String key){
        return redisTemplate.boundValueOps(key).get();
    }
}
