package com.recipe.command;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.recipe.model.Recipe;
import com.recipe.mongo.ConnectionProvider;

public class UpdateRecipeCommand {
	
	public boolean execute(String recipeName, Recipe recipe, boolean partialUpdate) throws JsonProcessingException {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeCollection = mdb.getCollection("recipeData");
		ObjectMapper mapper = new ObjectMapper();
		Document dbObject = new Document(Document.parse(mapper.writeValueAsString(recipe)));
		BasicDBObject recipeDBObject = new BasicDBObject(Document.parse(mapper.writeValueAsString(recipe)));
		
		try {
			
			BasicDBObject searchQuery = new BasicDBObject();      
		    searchQuery.put("name", recipeName);
		    
		    if(partialUpdate) {
		    	//recipeCollection.findOneAndUpdate(searchQuery, recipeDBObject);
		    	Document updateOperationDocument = new Document("$set", recipeDBObject);
		    	recipeCollection.findOneAndUpdate(searchQuery, updateOperationDocument);
		    	//recipeCollection.find(searchQuery);
		    	
		    }
		    else {
		    	recipeCollection.findOneAndReplace(searchQuery,dbObject );
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		finally{
			client.close();
		}
		return true;

	}

}
