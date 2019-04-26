package com.codetutr.utility;

import java.util.Random;
import java.util.UUID;

public class RandomGenerator {

	public static Long generateLong() {
		
		Long value = UUID.randomUUID().getMostSignificantBits() + getRandom();
		if (value < 0) {
			return Math.abs(value);
		}
		return value;
	}
	
	public static Long getRandom() {
		long lowerLimit = 123456712L;
		long upperLimit = 234567892L;
		Random r = new Random();
		return lowerLimit+((long)(r.nextDouble()*(upperLimit-lowerLimit)));
	}

}
