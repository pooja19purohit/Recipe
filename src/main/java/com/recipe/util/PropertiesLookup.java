package com.recipe.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLookup {

	Properties prop = new Properties();
	String filename = "config.properties";
	InputStream input = null;

	public PropertiesLookup() {
		try {
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return;
			}
			prop.load(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}


