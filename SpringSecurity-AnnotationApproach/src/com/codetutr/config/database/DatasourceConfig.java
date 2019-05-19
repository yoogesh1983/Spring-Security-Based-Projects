package com.codetutr.config.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DatasourceConfig {

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
				 * Set the Database
				 */
				.setType(EmbeddedDatabaseType.H2)
				
				/**
				 * Add the SQL script
				 */
				.addScript("/databaseScript/security-schema.sql").build();
	}
}
