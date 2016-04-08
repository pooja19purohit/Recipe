package com.recipe.recipes;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.command.CreateUserCommand;
import com.recipe.model.User;

@Path("/users")
public class UserService {
	ObjectMapper mapper = new ObjectMapper();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createUser(String userStr) {

		try {
			CreateUserCommand create = new CreateUserCommand();
			User user = mapper.readValue(userStr, User.class);
			boolean success = create.execute(user);
			if (success) {
				return Response.status(201).build();
			} else
				return Response.status(400).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.toString()).build();
		}
	}

}
