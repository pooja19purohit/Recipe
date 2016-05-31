package com.recipe.mongo;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

public class ConnectionProvider {

	public MongoClient getConnection() {
		try {

			MongoCredential credential = MongoCredential.createCredential("pooja19purohit", "recipe", "xxx".toCharArray());
			MongoClient client = new MongoClient(new ServerAddress("ds019990.mlab.com", Integer.valueOf("19990")),
					Arrays.asList(credential));
			
			return client;
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return null;

	}

}
