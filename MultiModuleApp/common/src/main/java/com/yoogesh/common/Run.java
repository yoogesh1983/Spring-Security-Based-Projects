package com.yoogesh.common;

public class Run {

	public static void main(String[] args) 
	{
		String maxAge = "7200";
		System.out.println("TTL1 ===========> " + maxAge);
		System.out.println("TTL1 ===========> " + Integer.valueOf(maxAge));
		System.out.println("TTL2 ===========> " + Long.parseLong(maxAge));
	}

}
