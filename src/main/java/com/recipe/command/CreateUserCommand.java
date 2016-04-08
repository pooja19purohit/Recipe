package com.recipe.command;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.recipe.model.User;
import com.recipe.mongo.ConnectionProvider;

public class CreateUserCommand {
	
	public boolean execute(User user) {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> booksColl = mdb.getCollection("userData");

		ObjectMapper mapper = new ObjectMapper();
		try {
			Document dbObject = new Document(Document.parse(mapper.writeValueAsString(user)));
			booksColl.insertOne(dbObject);
		} catch (Exception e) {
			System.out.println("ERROR during mapping book to Mongo Object");
			return false;
		}
		finally{
			client.close();
		}

		return true;
	}

}
