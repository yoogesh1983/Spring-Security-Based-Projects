package com.codetutr.config.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig_Persistance {

	@Bean
	public PlatformTransactionManager getTransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(getdataSource());
		return transactionManager;
	}
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getdataSource());
		return jdbcTemplate;
	}
	
	/**
	 *  Starting embedded database with following values: </br> 
	 *  {@link url=jdbc:h2:mem:dataSource}</br>
	 *  {@link DB_CLOSE_DELAY=-1}</br>
	 *  {@link DB_CLOSE_ON_EXIT=false}</br>
	 *  {@link username='sa'}</br>
	 *  
	 *   @return DataSource
	 */
	@Bean
	public DataSource getdataSource() {
		
		/**
		 * No need to shutdown since EmbeddedDatabaseFactoryBean will take care of this
		 */
		return new EmbeddedDatabaseBuilder()
				
				/**
				 * Set DataSource
				 */
				.setName("dataSource")
				
				/**
				 * Lets not get upset as we are only debugging ;-)
				 */
				.ignoreFailedDrops(true).continueOnError(true)
				
				/**
				 * DB Details:
				 */
				.setType(EmbeddedDatabaseType.H2).addScript("/databaseScript/security-schema.sql").build();
	}
}
