package com.yoogesh.common.Properties;

public class CustomProperties extends AbstractProperties
{
	public static final String PROPERTY_FILE_NAME = "custom.properties";
	private static CustomProperties instance;
	
	private final String authenticationRequestBean;
	private final String transactionId;
	private final String clientIpAddress;
	private final String browserAgent;
	private final String authenticationStateBean;
	private String authenticated;
	private String name;
	private String role;
	private String profileId;
	private String transactionHeaderName;
	
	protected CustomProperties() 
	{
		super(PROPERTY_FILE_NAME);
		
		authenticationRequestBean = getString("request.current.bean.name", null);
		transactionId = getString("request.header.transaction.id", null);
	    clientIpAddress = getString("request.header.client.address", null);
	    browserAgent = getString("request.header.browser.agent", null);
	    
	    authenticationStateBean = getString("request.web.current.bean.name", null);
	    
	    authenticated = getString("session.current.user.authenticated", null);
	    name = getString("session.current.user.name", null);	
	    role = getString("session.current.user.role", null);
	    profileId = getString("session.current.user.profileid", null);
	    
	    transactionHeaderName = getString("rest.header.param.name", "transactionIdentity");
	}
	
	
	public static synchronized CustomProperties getInstance()
	{
		if(instance == null)
		{
			instance = new CustomProperties();
		}
		return instance;
	}

	public String getAuthenticationRequestBean() 
	{
		return authenticationRequestBean;
	}
	
	public String getTransactionId() 
	{
		return transactionId;
	}

	public String getClientIpAddress() 
	{
		return clientIpAddress;
	}

	public String getBrowserAgent() 
	{
		return browserAgent;
	}
	
	public String getAuthenticationStateBean() 
	{
		return authenticationStateBean;
	}
	
	public String getAuthenticated() 
	{
		return authenticated;
	}

	public String getName() 
	{
		return name;
	}

	public String getRole() 
	{
		return role;
	}

	public String getProfileId() 
	{
		return profileId;
	}
	
	public String getTransactionHeaderName() 
	{
		return transactionHeaderName;
	}
	
}
