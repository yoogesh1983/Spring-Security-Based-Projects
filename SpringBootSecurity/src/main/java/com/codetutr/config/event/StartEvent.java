package com.codetutr.config.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartEvent
{
	@EventListener
	public void afterApplicationReady(ApplicationReadyEvent event) {
		//onStartup();
	}
	
}
