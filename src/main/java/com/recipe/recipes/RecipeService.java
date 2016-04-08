package com.recipe.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.command.CreateRecipeCommand;
import com.recipe.command.ListAllRecipesCommand;
/*import com.luckypants.command.DeleteBookCommand;
import com.luckypants.command.GetBookCommand;
import com.luckypants.command.ListAllBooksCommand;*/
import com.recipe.model.Recipe;

@Path("/books")
public class RecipeService {
	ObjectMapper mapper = new ObjectMapper();
	
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	@Path("person")
	public Response listBooks1() {
		String booksString = "Just checking";
		return Response.status(200).entity(booksString).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listBooks() {
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


	/*@GET
	@Path("/{key}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("key") String key,
			@PathParam("value") String value) {
		GetBookCommand getBookCommand = new GetBookCommand();
		Book book = getBookCommand.execute(key, value);
		String bookString = null;
		try {
			bookString = mapper.writeValueAsString(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(bookString).build();
	}*/

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
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

	/*@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBook(
			@DefaultValue("*") @QueryParam("query") String query,
			@DefaultValue("author") @QueryParam("sortby") String sortby) {
		HashMap<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("query", query);
		responseMap.put("sortby", sortby);
		String rString = "";
		try {
			rString = mapper.writeValueAsString(responseMap);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.toString()).build();
		}
		return Response.status(200).entity(rString).build();
	}

	@DELETE
	@Path("/{isbn}")
	public Response deleteBook(@PathParam("isbn") String isbn) {
		DeleteBookCommand delete = new DeleteBookCommand();
		delete.execute(isbn);
		return Response.status(200).build();
	}*/
}