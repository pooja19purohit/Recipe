package com.recipe.command;

import java.util.ArrayList;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.recipe.model.Recipe;
import com.recipe.mongo.ConnectionProvider;
import java.util.List;

public class SearchRecipeCommand {
	ObjectMapper mapper = new ObjectMapper();

	public ArrayList<Recipe> execute(String query) {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeCollection = mdb.getCollection("recipeData");
		
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		System.out.println("search query is " + query);
		
		try {
			BasicDBObject searchQuery = new BasicDBObject();      
		    searchQuery.put("name", query); 
			
			FindIterable<Document> cursor = recipeCollection.find(searchQuery);
			for (Document c : cursor) {
				Recipe b = mapper.convertValue(c, Recipe.class);

				recipes.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		finally{
			client.close();
		}
		return recipes;

	}
	
}