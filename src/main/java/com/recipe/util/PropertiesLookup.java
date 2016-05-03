package com.recipe.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLookup {

	Properties prop = new Properties();
	String filename = "config.properties";
	InputStream input = null;

	public PropertiesLookup() {
		try {
			input = getClass().getResourceAsStream("config.properties");
			if (input == null) {
				throw new FileNotFoundException("Sorry, unable to find " + filename);
			}
			else{
			prop.load(input);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}


