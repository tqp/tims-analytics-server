package com.timsanalytics.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final Environment environment;

    @Autowired
    public DataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    // AUTH DATA SOURCE

    @Bean(name = "mySqlAuthDataSource")
    public BasicDataSource mySqlAuthDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.environment.getProperty("mySql.datasource.driver-class-name"));
        dataSource.setUsername(this.environment.getProperty("mysql.datasource.username"));
        dataSource.setPassword(this.environment.getProperty("mysql.datasource.password"));
        dataSource.setUrl(this.environment.getProperty("mySql.datasource.url.auth"));
        return dataSource;
    }

    @Bean(name = "mySqlAuthJdbcTemplate")
    public JdbcTemplate mySqlAuthJdbcTemplate(@Qualifier("mySqlAuthDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "mySqlAuthTransactionManager")
    public PlatformTransactionManager mySqlAuthTransactionManager() {
        return new DataSourceTransactionManager(mySqlAuthDataSource());
    }

    // CHARTER SAUCE DATA SOURCE

    @Bean(name = "mySqlCharterSauceDataSource")
    public BasicDataSource mySqlCharterSauceDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.environment.getProperty("mySql.datasource.driver-class-name"));
        dataSource.setUsername(this.environment.getProperty("mysql.datasource.username"));
        dataSource.setPassword(this.environment.getProperty("mysql.datasource.password"));
        dataSource.setUrl(this.environment.getProperty("mySql.datasource.url.charter-sauce"));
        return dataSource;
    }

    @Bean(name = "mySqlCharterSauceJdbcTemplate")
    public JdbcTemplate mySqlCharterSauceJdbcTemplate(@Qualifier("mySqlAuthDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    // FUEL TRACKER DATA SOURCE

    @Bean(name = "mySqlFuelTrackerDataSource")
    public BasicDataSource mySqlFuelTrackerDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.environment.getProperty("mySql.datasource.driver-class-name"));
        dataSource.setUsername(this.environment.getProperty("mysql.datasource.username"));
        dataSource.setPassword(this.environment.getProperty("mysql.datasource.password"));
        dataSource.setUrl(this.environment.getProperty("mySql.datasource.url.fuel-tracker"));
        return dataSource;
    }

    @Bean(name = "mySqlFuelTrackerJdbcTemplate")
    public JdbcTemplate mySqlFuelTrackerJdbcTemplate(@Qualifier("mySqlFuelTrackerDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    // REALITY TRACKER DATA SOURCE

    @Bean(name = "mySqlRealityTrackerDataSource")
    public BasicDataSource mySqlRealityTrackerDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.environment.getProperty("mySql.datasource.driver-class-name"));
        dataSource.setUsername(this.environment.getProperty("mysql.datasource.username"));
        dataSource.setPassword(this.environment.getProperty("mysql.datasource.password"));
        dataSource.setUrl(this.environment.getProperty("mySql.datasource.url.reality-tracker"));
        return dataSource;
    }

    @Bean(name = "mySqlRealityTrackerJdbcTemplate")
    public JdbcTemplate mySqlRealityTrackerJdbcTemplate(@Qualifier("mySqlRealityTrackerDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
