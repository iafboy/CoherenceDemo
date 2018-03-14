package com.oracle.mw.sc.haier.Config;
import com.oracle.mw.sc.haier.Constants.CommonConstant;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.logging.Logger;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@AutoConfigureAfter(DBRepoMyBatisConfiguration.class)
public class MyBatisMapperScannerConfig {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(CommonConstant.BASEACCOUNTPATH+ ".dao");
        logger.debug("mapperScannerConfigurer created");
        return mapperScannerConfigurer;
    }
}
