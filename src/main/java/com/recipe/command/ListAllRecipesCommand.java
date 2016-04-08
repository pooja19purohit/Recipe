package com.recipe.command;

import java.util.ArrayList;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.model.Recipe;
import com.recipe.mongo.ConnectionProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ListAllRecipesCommand {
	ObjectMapper mapper = new ObjectMapper();

	public ArrayList<Recipe> execute() {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> booksColl = mdb.getCollection("recipeData");
		
		ArrayList<Recipe> books = new ArrayList<Recipe>();
		try {
			FindIterable<Document> cursor = booksColl.find();
			for (Document c : cursor) {
				Recipe b = mapper.convertValue(c, Recipe.class);

				books.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		finally{
			client.close();
		}
		return books;

	}
}