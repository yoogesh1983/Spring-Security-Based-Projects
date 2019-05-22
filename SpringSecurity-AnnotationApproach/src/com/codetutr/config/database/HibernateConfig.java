package com.codetutr.config.database;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.codetutr.utility.UtilityHelper;

public class HibernateConfig {

	@Autowired
	private DataSource dataSource;
	
	@Bean 
    public LocalSessionFactoryBean hibernateSessionFactory() throws IOException {
	   LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
       sessionFactory.setDataSource(dataSource);
       sessionFactory.setPackagesToScan(new String[] { "com.codetutr.entity" });
       sessionFactory.setHibernateProperties(UtilityHelper.getAdditionalProperties());
       sessionFactory.afterPropertiesSet();
       return sessionFactory;
    }
   
   	@Bean
    public HibernateTemplate getHibernateTemplate() throws IOException {
    	HibernateTemplate hibernateTemplate = new HibernateTemplate();
    	hibernateTemplate.setSessionFactory(hibernateSessionFactory().getObject());
    	hibernateTemplate.afterPropertiesSet();
    	return hibernateTemplate;
    }  
   	
   	
}
