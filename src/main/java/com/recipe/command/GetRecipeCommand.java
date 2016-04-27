package com.recipe.command;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.model.Recipe;
import com.recipe.mongo.ConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class GetRecipeCommand {
	ObjectMapper mapper = new ObjectMapper();

	public Recipe execute(String key, String value) {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeCollection = mdb.getCollection("recipeData");

		BasicDBObject searchQuery = new BasicDBObject();
		if (key.equals("_id")) {
			searchQuery.put(key, new ObjectId(value));
		} else {
			searchQuery.put(key, value);
		}
		Document result = null;
		try {
		FindIterable<Document> recipe = recipeCollection.find(searchQuery);
		result = recipe.first();
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		finally {
		client.close();
		}

		return mapper.convertValue(result, Recipe.class);
	}


}
