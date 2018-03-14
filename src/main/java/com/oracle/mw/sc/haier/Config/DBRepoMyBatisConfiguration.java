package com.oracle.mw.sc.haier.Config;

import java.io.File;

import javax.annotation.PostConstruct;

import com.oracle.mw.sc.haier.Constants.CommonConstant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
public class DBRepoMyBatisConfiguration implements TransactionManagementConfigurer {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required = true)
    DuridSettings duridSettings;

    @Autowired(required = true)
    DruidDataSource dataSource;

    @Autowired
    StatFilter sf;

    @Bean(name = "dataSource",initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        if (dataSource == null) {
            logger.info("dataSource() invoked");
            try {
                dataSource = new DruidDataSource();
                dataSource.setFilters(duridSettings.getFilters());
                dataSource.setInitialSize(duridSettings.getInitialSize());
                dataSource.setMaxActive(duridSettings.getMaxActive());
                dataSource.setMinIdle(duridSettings.getMinIdle());
                dataSource.setMaxWait(duridSettings.getMaxWait());
                dataSource.setTimeBetweenEvictionRunsMillis(duridSettings.getTimeBetweenEvictionRunsMillis());
                dataSource.setMinEvictableIdleTimeMillis(duridSettings.getMinEvictableIdleTimeMillis());
                dataSource.setValidationQuery(duridSettings.getValidationQuery());
                dataSource.setMaxPoolPreparedStatementPerConnectionSize(duridSettings.getMaxPoolPreparedStatementPerConnectionSize());
                dataSource.setUrl(duridSettings.getUrl());
                dataSource.setUsername(duridSettings.getUsername());
                dataSource.setPassword(duridSettings.getPassword());
                dataSource.setDriverClassName(duridSettings.getDriverClassName());
                dataSource.init();
            } catch (Exception e) {
                logger.error( e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            logger.debug("dataSource is ok!");
        }
        return dataSource;
    }

    @Bean(name="statfilter")
    public StatFilter statFilter(){
        sf=new StatFilter();
        sf.setLogSlowSql(duridSettings.logSlowSql);
        sf.setSlowSqlMillis(duridSettings.getSlowSqlMillis());
        logger.debug("StatFilter() initaled");
        return sf;
    }

    @PostConstruct
    public void checkConfigFileExists() {
        if (duridSettings == null) {
            throw new RuntimeException(
                    "Cannot find config (please add config file or check your Mybatis configuration)");
        }
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        logger.info("inital SqlSessionFactory");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.oracle.mw.sc.haier.DB.entity");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //bean.setMapperLocations(resolver.getResources("classpath:" + CommonConstant.BASEPACKAGEPATH + File.separator + "*.xml"));
            logger.info("SqlSessionFactory Created");
            return bean.getObject();
        } catch (Exception e) {
            logger.error( e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
//	@Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//	}
}