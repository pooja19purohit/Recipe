package com.recipe.recipes;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
import com.recipe.command.CreateImageCommand;
import com.recipe.command.CreateRecipeCommand;
import com.recipe.command.ListAllRecipesCommand;
import com.recipe.command.SearchRecipeCommand;
import com.recipe.command.DeleteRecipeCommand;
import com.recipe.command.UpdateRecipeCommand;
import com.recipe.command.GetRecipeCommand;
import com.recipe.model.Recipe;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	
	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getRecipeById(@PathParam("id") String id)
	{
		GetRecipeCommand getRecipeCommand = new GetRecipeCommand();
		Recipe recipe = getRecipeCommand.execute(id);
		String recipeString = null;
		try {
			recipeString = mapper.writeValueAsString(recipe);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
		return Response.status(200).entity(recipeString).build();
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
	
	@POST
	@Path("/simplePostRecipe")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createRecipe(@FormParam("recipeName") String name,@FormParam("direction") String direction) {
	try {
		CreateRecipeCommand create = new CreateRecipeCommand();
		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setDirection(direction);
		boolean success = create.execute(recipe);
		if (success) {
		return Response.status(201).entity("recipe successfully created").build();
		}
		else
		return Response.status(400).build();
		}
	catch (Exception e) {
		return Response.status(400).entity(e.toString()).build();
	}
}
	
	//TODO: Pagination
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRecipe(@QueryParam("q") String query) {

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
	
	@DELETE
    @Path("/deleteById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response deleteRecipeById(@PathParam("id") String id) {
		try {
		DeleteRecipeCommand delete = new DeleteRecipeCommand();
		boolean success = delete.execute("_id" , id);
		if (success) {
			return Response.status(200).entity("Recipe deleted successfully").build();
		}
		else
			return Response.status(400).entity("There was a problem while deleting the recipe from the database").build();
		}
		catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}


	@PUT
	@Path("/updateAll/{recipeName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response updateRecipe(String recipeStr, @PathParam("recipeName") String recipeName) {

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
	
	/*@PUT
	@Path("/put/{recipeName}/{fieldName}/{fieldValue}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response updateRecipeField(String recipeStr, @PathParam("recipeName") String recipeName, @PathParam("fieldName") String fieldName, @PathParam("fieldValue") String fieldValue) {

		try {
			UpdateRecipeCommand update = new UpdateRecipeCommand();
			Recipe recipe = mapper.readValue(recipeStr, Recipe.class);
			boolean success = update.execute(recipeName, fieldName, fieldValue, recipe);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}*/

	/*
	 * Get first recipe which satisfies the Key:Value combination 
	 */
	@GET
	@Path("/getone/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecipeForAtttribute(@PathParam("key") String key, @PathParam("value") String value) {
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
	
	@GET
    @Path("/search/filter/{key}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response searchRecipeWithFilter(@QueryParam("q") String
	query,@PathParam("key") String key, @PathParam("value") String value)
	{
	SearchRecipeCommand listRecipes = new SearchRecipeCommand();
	ArrayList<Recipe> list = listRecipes.execute(query,key,value);
	String booksString = null;
	try {
	booksString = mapper.writeValueAsString(list);
	}
	catch (Exception e) {
	e.printStackTrace();
	return Response.status(400).entity(e.toString()).build();
	}
	return Response.status(200).entity(booksString).build();
	}
	
	//TODO: Link to recipe
	@POST
	@Path("/addImage")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response addImage(String recipeStr) {
		CreateImageCommand image = new CreateImageCommand();
		boolean result = image.execute();
		if(result) {
			return Response.status(201).entity("Added successfully").build();
		}
		else {
			return Response.status(400).entity("Could not add image").build();
		}
	}
	

}