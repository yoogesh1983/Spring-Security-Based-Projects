package com.codetutr.config.event;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import org.springframework.stereotype.Component;

@Component
@WebListener
public class RequestEvent implements ServletRequestListener
{
	@Override
	public void requestDestroyed (ServletRequestEvent event) {
    }

	@Override
    public void requestInitialized (ServletRequestEvent event) {
    }


}
