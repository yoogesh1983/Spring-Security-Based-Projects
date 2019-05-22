package com.codetutr.config.database;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(value={DatasourceConfig.class, SpringJDBCConfig.class, SpringJPAConfig.class, HibernateConfig.class})
public class AppConfig_Persistance {

	private DataSource datasource; 
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	private SessionFactory sessionFactory;
	
	@Autowired
	private AppConfig_Persistance(DataSource datasource, LocalContainerEntityManagerFactoryBean entityManagerFactoryBean, SessionFactory sessionFactory){
		this.datasource = datasource;
		this.entityManagerFactoryBean = entityManagerFactoryBean;
		this.sessionFactory = sessionFactory;
	}
	
	
	/**
	 * This {@link TransactionManager} is for {@link SpringJdbc}
	 */
	@Bean(name="springJdbcTransactionManager")
	public PlatformTransactionManager getSpringJdbcTransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(datasource);
		return transactionManager;
	}
	
	
	/**
	 * This {@link TransactionManager} is for {@link Traditional Hibernate}
	 */
	@Bean(name="hibernateTransactionManager")
    public PlatformTransactionManager getSpringHibernateTransactionManager() {
    	HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
	
	/**
	 * This {@link TransactionManager} is for {@link SpringJPA} and {@link SpringData}. Since the {@link SpringData} has internally used a bean name as {@link transactionManager}, 
	 * we cannot rename it.Otherwise it will give an exception.
	 */
	@Bean(name="transactionManager")
	public PlatformTransactionManager getSpringJpaTransactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager; 
	}
}
