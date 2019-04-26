package com.codetutr.config.database;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class AppConfig_Persistance {

	@Bean
	public DataSource getdataSource() {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		return new EmbeddedDatabaseBuilder()
				// Starting embedded database:
				// url='jdbc:h2:mem:dataSource;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false',
				// username='sa'
				.setName("dataSource")
				// Lets not get upset as we are only debugging ;-)
				.ignoreFailedDrops(true).continueOnError(true)
				// DB Details:
				.setType(EmbeddedDatabaseType.H2).addScript("/databaseScript/security-schema.sql")
				.addScript("/databaseScript/security-users.sql")
				.addScript("/databaseScript/security-user-authorities.sql").build();
	}

	@PostConstruct
	public void dataSourceInitialization() {
		
	}

	@PreDestroy()
	public void dataSourceDestroy() throws SQLException {
	}

}
