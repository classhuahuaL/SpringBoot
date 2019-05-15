package com.class7no.springbootmybatisapi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author huahua
 * 配置类
 */
@Configuration
public class MyBatisConfig {

    /**
     * 配置数据源
     * @return
     */
    @Bean(name = "dataSource",destroyMethod = "close")
    public DataSource dataSource(){
        //阿里Druid连接池
        DruidDataSource druidDataSource = new DruidDataSource();
        //数据库url
        druidDataSource.setUrl("");
        //数据库账号
        druidDataSource.setUsername("");
        //数据库密码
        druidDataSource.setPassword("");
        //连接驱动
        druidDataSource.setDriverClassName("");

        // 每个分区最大的连接数
        druidDataSource.setMaxActive(20);
        // 每个分区最小的连接数
        druidDataSource.setMinIdle(5);
        return druidDataSource;
    }

    /**
     * 配置mybatis连接
     * @return
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(){
        //创建SqlSessionFactory 的实现类
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        try{
            //设置连接池
            sqlSessionFactoryBean.setDataSource(dataSource());

            //设置别名包的位置
            sqlSessionFactoryBean.setTypeAliasesPackage("");

            //设置读取xml文件
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:*.xml"));
            //-- 加载mybatis的全局配置文件
            Resource mybatisConfigXml = resourcePatternResolver.getResource("classpath:mybatis/mybatis-config.xml");
            sqlSessionFactoryBean.setConfigLocation(mybatisConfigXml);

            //return SqlSessionFactory
            return sqlSessionFactoryBean.getObject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
