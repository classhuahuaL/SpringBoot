package com.class7no.springbootmybatisapi.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huahua
 * 扫描mapper接口映射
 */
@Configuration
public class MyBatisMapperScannerConfig {

    @Bean(value = "mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //设置mybatis sqlSession工厂
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //设置mapper接口地址
        mapperScannerConfigurer.setBasePackage("com.class7no.springbootmybatisapi.mapper");
        return mapperScannerConfigurer;
    }
}
