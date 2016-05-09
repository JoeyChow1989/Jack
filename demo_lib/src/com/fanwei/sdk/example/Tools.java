package com.fanwei.sdk.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


/**
 * @author zhoup
 */
public class Tools {
	
	public static String setRand() {
		String rad = "0123456789";
		StringBuffer result = new StringBuffer();
		Random rand = new Random();
		int length = 8;
		for (int i = 0; i < length; i++) {
			int randNum = rand.nextInt(10);
			result.append(rad.substring(randNum, randNum + 1));
		}
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss",new Locale("zh", "CN"));
		return (df.format(new Date())+result).toString();
	}
}
