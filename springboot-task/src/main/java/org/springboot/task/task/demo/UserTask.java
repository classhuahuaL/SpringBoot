package org.springboot.task.task.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: Test
 * @description:
 * @author: HuaHua.L
 * @date: 2019-11-29 11:49
 **/
@Component
public class UserTask {

    /**
     * 添加注解表示该定时器启动
     * cron 定时任务表达式
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void test01(){
        System.out.println("task load");
    }
}
