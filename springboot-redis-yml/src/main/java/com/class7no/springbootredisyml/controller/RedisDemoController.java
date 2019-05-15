package com.class7no.springbootredisyml.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisDemoController {

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/save")
    public String saveRedis(@RequestParam("msg") String msg){
        redisTemplate.boundValueOps("111").set(msg);
        return msg;
    }
    @GetMapping("/find")
    public Object find(@RequestParam("key") String key){
        return redisTemplate.boundValueOps(key).get();
    }
}
