package com.class7no.springbootmybatisapi.controller;

import com.class7no.springbootmybatisapi.mapper.SysRegisteredMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {

    @Resource
    private SysRegisteredMapper sysRegisteredMapper;

    @GetMapping("/findAll")
    public List<Map<String,Object>> findAll(){
        return sysRegisteredMapper.findAll();
    }
}
