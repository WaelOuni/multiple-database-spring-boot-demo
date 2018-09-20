package com.example.demo;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages = "com.example.demo.repo.postgres", entityManagerFactoryRef = "postgresEntityManager", transactionManagerRef = "postgresTransactionManager")
public class PostgresDbConfig {
	@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean postgresEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(postgresDataSource());
		em.setPackagesToScan(new String[] { "com.example.demo.model.postgres" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("spring.jpa.properties.hibernate.dialect",
				env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("spring.jpa.show-sql", env.getProperty("spring.jpa.show-sql"));

		// Bug de PostgreSQL:La fonction createClob() n'est pas encore implémentée.
		properties.put("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation",
				env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));

		properties.put("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false",
				env.getProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean
	public DataSource postgresDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));

		return dataSource;
	}

	@Primary
	@Bean
	public PlatformTransactionManager postgresTransactionManager() {
		System.out.println("postgresEntityManager().getObject()");
		for (Entry<String, Object> entry : postgresEntityManager().getObject().getProperties().entrySet()) {
			System.out.println("Item : " + entry.getKey() + " : " + entry.getValue());
		}
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(postgresEntityManager().getObject());
		return transactionManager;
	}
}