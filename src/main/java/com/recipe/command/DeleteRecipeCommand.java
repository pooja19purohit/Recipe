package com.recipe.command;

import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.recipe.mongo.ConnectionProvider;

public class DeleteRecipeCommand {
	
	public boolean execute(String recipeName) {
		
		
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeColl = mdb.getCollection("recipeData");
		try {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", recipeName);

		recipeColl.deleteOne(searchQuery);
		client.close();
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		
		
		return true;
	}
}