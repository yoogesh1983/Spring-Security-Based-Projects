package com.yoogesh.mvc.web.event;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.security.service.AbstractUserService;

@Component
public class StartEvent <U extends AbstractUser<U,ID>, ID extends Serializable>
{
	/**
	 * If you do any one of below , then you don't need to do "<U extends AbstractUser<U,ID>, ID extends Serializable>"
	 * 
	 * @Autowired 
	 * private ProfileService profileService;
	 * 
	 * @Autowired
	 * private AbstractUserService<?,?> profileService;
	 */
	
	@Autowired 
	@Lazy
	private AbstractUserService<U,ID> profileService;
	
	@EventListener
	public void afterApplicationReady(ApplicationReadyEvent event) {
		profileService.onStartup();
	}
	
}
