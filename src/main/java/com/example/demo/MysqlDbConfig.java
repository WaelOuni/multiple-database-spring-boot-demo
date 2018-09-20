package com.example.demo;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = "com.example.demo.repo.mysql", entityManagerFactoryRef = "EntityManager", transactionManagerRef = "TransactionManager")
public class MysqlDbConfig {
	@Autowired
	private Environment env;

	@Bean
	public EntityManagerFactory EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(DataSource());
		em.setPackagesToScan(new String[] { "com.example.demo.model.mysql" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("spring.jpa.properties.hibernate.dialect",
				env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("spring.jpa.show-sql", env.getProperty("spring.jpa.show-sql"));
		em.setJpaPropertyMap(properties);
		em.afterPropertiesSet();
		return em.getObject();
	}

	@Bean
	public DataSource DataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.mysql.datasource.driverClassName"));
		dataSource.setUrl(env.getProperty("spring.mysql.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.mysql.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.mysql.datasource.password"));

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager TransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(EntityManager());
		return transactionManager;
	}
}