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
/*import com.allanbank.mongodb.builder.Text;
import com.allanbank.mongodb.builder.TextResult;*/


/*
 * https://scalegrid.io/blog/fast-paging-with-mongodb/
 * var simpleSearch = function(request,response){
	var query = request.params.query;
	var pagenum = request.params.pagenum;
	var startIndex = (pagenum-1)*3;

	console.log("Query for: "+query+', page number: '+pagenum);

	Post.search({
		"query":{
			"fuzzy":{
				"author":{
					"value":query,
					"fuzziness":2
				},
				"career_title":{
					"value":query,
					"fuzziness":2
				}
			}
		}
	}, function(err,result){
		console.log("Fuzzy search made.");
		//console.log("Result: "+JSON.stringify(result));
		console.log(JSON.stringify(result.hits.hits));
		var career_titles = [];
		var authors = [];
		var descriptions = [];
		var _ids = [];

		for (var i = startIndex; i < startIndex+3 && i < result.hits.hits.length; i++){
			career_titles.push(result.hits.hits[i]._source.career_title);
			authors.push(result.hits.hits[i]._source.author);
			descriptions.push(result.hits.hits[i]._source.description);
			_ids.push(result.hits.hits[i]._id);
		}

		console.log(career_titles[0]);

		response.render('searchResult',
			{
				'career_titles' : {
					title1 : career_titles[0],
					title2 : career_titles[1],
					title3 : career_titles[2]
				},
				'authors' : {
					author1 : authors[0],
					author2 : authors[1],
					author3 : authors[2]
				},
				'descriptions' : {
					description1 : descriptions[0],
					description2 : descriptions[1],
					description3 : descriptions[2]
				},
				'_ids' : {
					id1 : _ids[0],
					id2 : _ids[1],
					id3 : _ids[2]
				}
			});

	});
}
 */
public class SearchRecipeCommand {
	ObjectMapper mapper = new ObjectMapper();

	public ArrayList<Recipe> execute(String query) {
		MongoClient client = (new ConnectionProvider()).getConnection();
		MongoDatabase mdb = client.getDatabase("recipe");
		MongoCollection<Document> recipeCollection = mdb.getCollection("recipeData");
		//DB db = client.getDB("recipe");
		
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		System.out.println("search query is " + query);
		
		try {
			
			/*DBObject searchCmd = new BasicDBObject();
			searchCmd.put("text", recipeCollection); // the name of the collection (string)
			searchCmd.put("search", query); // the term to search for (string)
			CommandResult commandResult = db.command(searchCmd);
			System.out.println("Results" + commandResult.get("results"));*/
			/*List<TextResult> results = collection.textSearch(Text.builder()
	                .searchTerm("coffee").limit(10));*/
			BasicDBObject searchQuery = new BasicDBObject();      
		    String queryString = "^" + query.trim() ;   // setup regex
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