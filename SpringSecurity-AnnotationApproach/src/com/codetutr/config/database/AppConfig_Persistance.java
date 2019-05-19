package com.codetutr.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(value={DatasourceConfig.class, SpringJDBCConfig.class, SpringDataJPAConfig.class})
public class AppConfig_Persistance {

	private DataSource datasource; 
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	
	@Autowired
	private AppConfig_Persistance(DataSource datasource, LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){
		this.datasource = datasource;
		this.entityManagerFactoryBean = entityManagerFactoryBean;
	}
	
	@Bean(name="jdbcTemplateTransactionManager")
	public PlatformTransactionManager getJdbcTemplateTransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(datasource);
		return transactionManager;
	}
	
	@Bean(name="jpaTransactionManager")
	public PlatformTransactionManager getJpaTransactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager; 
	}
}
