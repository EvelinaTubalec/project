package com.leverx.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.leverx.repositories")
@PropertySource(value = {"classpath:application.properties"})
@EnableTransactionManagement
public class DataSourceConfig {

  @Value("${database.url}")
  public String url;

  @Value("${database.driverName}")
  public String driverName;

  @Value("${database.username}")
  public String username;

  @Value("${database.password}")
  public String password;

  @Value("${database.poolSize}")
  public Integer poolSize;

  @Value("${hibernate.dialect}")
  public String dialect;

  @Value("${hibernate.show_sql}")
  public String showSql;

  @Value("${hibernate.hbm2ddl.auto}")
  public String hbm2ddl;

  @Value("${hibernate.format_sql}")
  public String formatSql;

  @Bean
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setDriverClassName(driverName);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);
    hikariConfig.setMaximumPoolSize(poolSize);

    return new HikariDataSource(hikariConfig);
  }

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("com.leverx.model");

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(getAdditionalProperties());

    return em;
  }

  private Properties getAdditionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", dialect);
    properties.setProperty("hibernate.show_sql", showSql);
    properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
    properties.setProperty("hibernate.format_sql", formatSql);
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  @Bean
  public SpringLiquibase liquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
    liquibase.setDataSource(dataSource());
    return liquibase;
  }
}
