package com.zj.datesourcetest.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zj.datesourcetest.master",sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    private static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    @Value("${master.dataSource.url}")
    private String url;

    @Value("${master.dataSource.username}")
    private String username;

    @Value("${master.dataSource.password}")
    private String password;

    @Value("${master.dataSource.driver-class-name}")
    private String driverClassName;


    @Primary
    @Bean("masterDataSource")
    public DataSource getMasterDataSource(){
        //创建一个数据源对象，springboot默认为Hikari数据源，我们也用该数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        return dataSource;



    }

    @Bean("masterTransactionManager")
    @Primary
    public TransactionManager getMasterTransactionManager(){
        return new DataSourceTransactionManager
                ( getMasterDataSource());
    }

    @Bean("masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(masterDataSource);
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION)

        );
        return sqlSessionFactory.getObject();


    }
}
