package com.yoogesh.LoggerHelper;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.yoogesh.common.LoggerHelper.TrackingLogger;



public class TrackingLoggerTest
{	
	@Test
	public void StringAppenderTestForLogger()
	{
		Logger logger = TrackingLogger.getLogger(this.getClass());
		TrackingLogger.setTransactionId("1ZYZAE454YTCREB4WZY6E");
		Appender appender = new StringAppender();
		logger.addAppender(appender);

		logger.info("Executing INFO.....");
		logger.debug("Error occured while executing INFO..... ", new RuntimeException("NullPointer Exception Ocurred"));
		System.out.println(appender.toString());
		//Approvals.verify(appender.toString());
	}
	
}
