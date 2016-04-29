package com.recipe.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.recipe.command.CreateRecipeCommand;
import com.recipe.command.ListAllRecipesCommand;
import com.recipe.command.SearchRecipeCommand;
import com.recipe.command.DeleteRecipeCommand;
import com.recipe.command.UpdateRecipeCommand;
import com.recipe.command.GetRecipeCommand;
import com.recipe.model.Recipe;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Properties;
import com.recipe.util.PropertiesLookup;

@Path("/recipes")
public class RecipeService {
	ObjectMapper mapper = new ObjectMapper();
	
	//https://unhrecipe.herokuapp.com/rest/recipes
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listRecipes() {
		ListAllRecipesCommand listRecipes = new ListAllRecipesCommand();
		ArrayList<Recipe> list = listRecipes.execute();
		String booksString = null;
		try {
			booksString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(booksString).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/createRecipe")
	//@Consumes("application/x-www-form-urlencoded")
	public Response createRecipe(String recipeStr) {

		try {
			CreateRecipeCommand create = new CreateRecipeCommand();
			Recipe recipe = mapper.readValue(recipeStr, Recipe.class);
			boolean success = create.execute(recipe);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}
	
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRecipe(@QueryParam("q") String query, @QueryParam("offset") int offset, @QueryParam("count") int count) {

		SearchRecipeCommand listRecipes = new SearchRecipeCommand();
		ArrayList<Recipe> list = listRecipes.execute(query);
		String booksString = null;
		try {
			booksString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(booksString).build();
	}
	
	@DELETE
	@Path("/delete/{recipeName}")
	public Response deleteBook(@PathParam("recipeName") String recipeName) {
		try {
		DeleteRecipeCommand delete = new DeleteRecipeCommand();
		boolean success = delete.execute(recipeName);
		
		if (success) {
			return Response.status(200).build();
		} else
			return Response.status(400).build();
	} catch (Exception e) {
		return Response.status(400).entity(e.toString()).build();
	}
	}
	
	@PUT
	@Path("/put/{recipeName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createBook(String recipeStr, @PathParam("recipeName") String recipeName) {

		try {
			UpdateRecipeCommand update = new UpdateRecipeCommand();
			Recipe recipe = mapper.readValue(recipeStr, Recipe.class);
			boolean success = update.execute(recipeName, recipe);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}
	
	/*
	 * Get first recipe which satisfies the Key:Value combination 
	 */
	@GET
	@Path("/getone/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecipeForAtttribute(@PathParam("key") String key, @PathParam("value") String value) {
		System.out.println(key + value);
		GetRecipeCommand getRecipeCommand = new GetRecipeCommand();
		Recipe recipe = getRecipeCommand.execute(key, value);
		String recipeString = null;
		try {
			recipeString = mapper.writeValueAsString(recipe);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
		return Response.status(200).entity(recipeString).build();
	}
	
	
	@GET
	@Path("/getappdetails")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getAppDetails() {
	String result = "";
	try {
	ObjectNode appInfo = mapper.createObjectNode();
	PropertiesLookup pl = new PropertiesLookup();
	appInfo.put("ApplicationName", pl.getProperty("ProjectName"));
	appInfo.put("team", pl.getProperty("team"));
	appInfo.put("Version", pl.getProperty("Version"));
	appInfo.put("course", pl.getProperty("course"));
	result = mapper.writeValueAsString(appInfo);
	}
	catch(Exception e) {
	System.out.println(e.getMessage());
	return Response.status(400).entity(e.toString()).build();
	}
	return Response.status(200).entity(result).build();
	}
	
	
	
}