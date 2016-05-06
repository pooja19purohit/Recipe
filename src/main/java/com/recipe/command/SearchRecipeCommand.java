package com.recipe.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mongodb.BasicDBList;
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
	//List<String> recipeAttributes = Arrays.asList("name", "difficultyLevel", "recipeCategory" , "cuisine", "prepTime", "cookTime", "ingredients", "direction", "additionalNote", "yields");

	public ArrayList<Recipe> execute(String query) {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeCollection = mdb.getCollection("recipeData");
		
		recipeCollection.createIndex(new BasicDBObject("$**","text"));
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		System.out.println("search query is " + query);
		
		try {
			/*BasicDBObject searchQuery = new BasicDBObject();      
		    searchQuery.put("text", query); */
		    Document textSearch = new Document("$text", new Document("$search", query));
			FindIterable<Document> cursor = recipeCollection.find(textSearch);
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
	
	public ArrayList<Recipe> execute(String query, String filterField, String filterValue, boolean or) {
		
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeCollection = mdb.getCollection("recipeData");
		BasicDBObject andQuery;
		FindIterable<Document> cursor;
		
		recipeCollection.createIndex(new BasicDBObject("$**","text"));
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		BasicDBObject searchQuery = new BasicDBObject();
		if (filterField.equals("_id")) {
			searchQuery.put(filterField, new ObjectId(filterValue));
		} else {
			searchQuery.put(filterField, filterValue);
		}
		
		try {
			/*BasicDBObject searchQuery = new BasicDBObject();      
		    searchQuery.put("text", query); */
		    Document textSearch = new Document("$text", new Document("$search", query));
		    BasicDBList and = new BasicDBList();
			and.add(searchQuery);
			and.add(textSearch);
			if(or) {
				System.out.println("or");
				cursor = recipeCollection.find(textSearch).filter(searchQuery);
			}
			else {
				System.out.println("and");
				andQuery = new BasicDBObject("$and", and);
				cursor = recipeCollection.find(andQuery);
			}
			//FindIterable<Document> cursor = recipeCollection.find(andQuery);

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