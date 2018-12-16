package com.yoogesh.common.RestHelper;


public class MySpringRequestFactory
{
	public static <Type> MySpringRequest<Type>  getRequest(Type data)
	{
		MySpringRequest <Type> request = new MySpringRequest<>();
		request.setData(data);
		return request;
	}
}
