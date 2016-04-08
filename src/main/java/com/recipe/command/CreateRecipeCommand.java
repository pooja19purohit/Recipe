package com.recipe.command;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.model.Recipe;
import com.recipe.mongo.ConnectionProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CreateRecipeCommand {

	public boolean execute(Recipe recipe) {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> booksColl = mdb.getCollection("recipeData");

		ObjectMapper mapper = new ObjectMapper();
		try {
			Document dbObject = new Document(Document.parse(mapper.writeValueAsString(recipe)));
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