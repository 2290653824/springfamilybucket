package com.zj.datesourcetest.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zj.datesourcetest.second",sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDataSourceConfig {

    private static final String MAPPER_LOCATION = "classpath:mapper/second/*.xml";

    @Value("${second.dataSource.url}")
    private String url;

    @Value("${second.dataSource.username}")
    private String username;

    @Value("${second.dataSource.password}")
    private String password;

    @Value("${second.dataSource.driver-class-name}")
    private String driverClassName;



    @Bean("secondDataSource")
    public DataSource getSecondDataSource(){
        //创建一个数据源对象，springboot默认为Hikari数据源，我们也用该数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        return dataSource;



    }

    @Bean("secondTransactionManager")
    public TransactionManager getSecondTransactionManager(){
        return new DataSourceTransactionManager
                ( getSecondDataSource());
    }

    @Bean("secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource secondDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(secondDataSource);
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION)

        );
        return sqlSessionFactory.getObject();


    }
}
