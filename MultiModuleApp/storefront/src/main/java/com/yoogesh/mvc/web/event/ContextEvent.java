package com.yoogesh.mvc.web.event;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.yoogesh.common.Profiles;
import com.yoogesh.common.Properties.LogProperties;

public class ContextEvent implements ApplicationContextInitializer<ConfigurableApplicationContext> 
{
    private final Logger logger = LoggerFactory.getLogger(ContextEvent.class);

    private static final String ENV_TARGET = "envTarget";

    public ContextEvent() 
    {
        super();
    }
    
    

    
    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) 
    {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String envTarget = null;
        try {
            envTarget = getEnvTarget(environment);
            environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:env-" + envTarget + ".properties"));

            final String activeProfiles = environment.getProperty("spring.profiles.active");
            String[] profiles = activeProfiles.split(",");
            environment.setActiveProfiles(profiles);
            setSystemProperties(profiles);
        } catch (final IOException ioEx) {
            if (envTarget != null) {
                logger.warn("Didn't find env-" + envTarget + ".properties in classpath so not loading it in the AppContextInitialized", ioEx);
            }
        }
    }


    
    
    private String getEnvTarget(final ConfigurableEnvironment environment) 
    {
        String target = environment.getProperty(ENV_TARGET);
        if (target == null) {
            logger.warn("Didn't find a value for {} in the current Environment!", ENV_TARGET);
        }

        if (target == null) {
            logger.info("Didn't find a value for {} in the current Environment!, using the default `local`", ENV_TARGET);
            target = Profiles.LOCAL;
        }

        return Preconditions.checkNotNull(target);
    }
    
    

    
    private void setSystemProperties(String[] profiles) 
    {
    	String currentProfile = null;
    	List<String> activeProfiles = Arrays.asList(profiles);	
    	if (!CollectionUtils.isEmpty(activeProfiles)) 
		{
			for (String next : activeProfiles) 
			{
				do {
					currentProfile = next;
				} while (StringUtils.isBlank(next));
			}
		}
    	if(StringUtils.isNotBlank(currentProfile)){
    		logger.info("setting system properties for RuntimeEnvironment as {}", currentProfile);
			System.setProperty(LogProperties.CURRENT_ENVIRONMENT, currentProfile);
		}
		else{
			logger.info("Didn't find a value for {} in the current Environment!, setting system properties for RuntimeEnvironment the default `local`", ENV_TARGET);
			System.setProperty(LogProperties.CURRENT_ENVIRONMENT, Profiles.LOCAL);
		}
	}

}
