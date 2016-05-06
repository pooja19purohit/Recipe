/*
 * Web Services
 * Authors: Pooja Purohit & Keertana HS
 * 
 */
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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

	/*
	 * Rest Services created by Pooja: POST, GET, PUT , DELETE
	 */
	// https://unhrecipe.herokuapp.com/rest/recipes
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
			return Response.status(400).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(booksString).build();
	}

	@GET
	@Path("/getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecipeById(@PathParam("id") String id) {
		GetRecipeCommand getRecipeCommand = new GetRecipeCommand();
		Recipe recipe = getRecipeCommand.execute(id);
		String recipeString = null;
		try {
			recipeString = mapper.writeValueAsString(recipe);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(recipeString).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Path("/createRecipe")
	public Response createRecipe(String recipeStr) {

		try {
			CreateRecipeCommand create = new CreateRecipeCommand();
			Recipe recipe = mapper.readValue(recipeStr, Recipe.class);
			boolean success = create.execute(recipe);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).entity("There was an error while creating recipe. Please check you connection or data").build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
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
				return Response.status(400).entity("There was an error while deleting recipe").build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}

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
			return Response.status(400).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(booksString).build();
	}
	
	//Updates the fields
	@PUT
	@Path("/partialUpdate/{recipeName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response updateRecipeField(String recipeStr,
			@PathParam("recipeName") String recipeName) {
		// Update fields which are sent, without replacing the whole document
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			UpdateRecipeCommand update = new UpdateRecipeCommand();
			Recipe recipe = mapper.readValue(recipeStr, Recipe.class);

			boolean success = update.execute(recipeName, recipe, true);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).entity("There was an error while updating the document").build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}

	/*
	 * Get first recipe which satisfies the Key:Value combination
	 */
	@GET
	@Path("/getone/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecipeForAtttribute(@PathParam("key") String key,
			@PathParam("value") String value) {
		GetRecipeCommand getRecipeCommand = new GetRecipeCommand();
		Recipe recipe = getRecipeCommand.execute(key, value);
		String recipeString = null;
		try {
			recipeString = mapper.writeValueAsString(recipe);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(recipeString).build();
	}

	/*
	 * Web Services created by Keertana HS: POST, GET, PUT , DELETE
	 */
	//Simple post when post is called directly from a form
	@POST
	@Path("/simplePostRecipe")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createRecipe(@FormParam("recipeName") String name,
			@FormParam("direction") String direction) {
		try {
			CreateRecipeCommand create = new CreateRecipeCommand();
			Recipe recipe = new Recipe();
			recipe.setName(name);
			recipe.setDirection(direction);
			boolean success = create.execute(recipe);
			if (success) {
				return Response.status(201).entity("recipe successfully created").build();
			} else
				return Response.status(400).entity("Error while posting recipe").build();
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
			boolean success = delete.execute("_id", id);
			if (success) {
				return Response.status(200).entity("Recipe deleted successfully").build();
			} else
				return Response.status(400).entity("There was a problem while deleting the recipe from the database").build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}
	
	//Replaces the document
	@PUT
	@Path("/updateAll/{recipeName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response updateRecipe(String recipeStr,
			@PathParam("recipeName") String recipeName) {

		try {
			UpdateRecipeCommand update = new UpdateRecipeCommand();
			Recipe recipe = mapper.readValue(recipeStr, Recipe.class);
			boolean success = update.execute(recipeName, recipe, false);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).entity("There was an error while replacing document in the database").build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(400).entity(e.toString()).build();
		}
		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/search/filter/{key}/{value}/{or}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRecipeWithFilter(@QueryParam("q") String query,
			@PathParam("key") String key, @PathParam("value") String value,
			@PathParam("or") boolean or) {
		// Boolean b = Boolean.valueOf(or);
		SearchRecipeCommand listRecipes = new SearchRecipeCommand();
		ArrayList<Recipe> list = listRecipes.execute(query, key, value, or);
		String booksString = null;
		try {
			booksString = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.toString()).build();
		}
		return Response.status(200).entity(booksString).build();
	}

	/*
	 * Pooja
	 * Need to link to recipe yet
	 */
	// TODO: Link each image to recipe
	@POST
	@Path("/addImage")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response addImage(String recipeStr) {
		CreateImageCommand image = new CreateImageCommand();
		boolean result = image.execute();
		if (result) {
			return Response.status(201).entity("Added successfully").build();
		} else {
			return Response.status(400).entity("Could not add image").build();
		}
	}

}