package com.recipe.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;

import com.recipe.util.ObjectIdJsonSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.MoreObjects;
import java.util.Objects;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
	//@JsonDeserialize(as=ArrayList.class, contentAs=String.class)

	//@JsonProperty("id")
	
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonProperty("id")
	ObjectId id;
	String name;
	String difficultyLevel;
	String recipeCategory;
	String cuisine;
	String prepTime;
	String cookTime;
	String recipePhotoLink;
	@JsonDeserialize(as=ArrayList.class, contentAs=HashMap.class)
	ArrayList<HashMap<String,Object>> ingredients;
	String direction;
	String additionalNote;
	String yields;
	HashMap<String,String> nutritionFacts;
	
	
	
	//@JsonSerialize(using=ObjectIdJsonSerializer.class)
	@JsonProperty("id")
	public ObjectId getId() {
		return id;
	}
	
	@JsonProperty("id")
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	public String getRecipeCategory() {
		return recipeCategory;
	}
	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}
	public String getCuisine() {
		return cuisine;
	}
	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public String getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(String prepTime) {
		this.prepTime = prepTime;
	}
	public String getCookTime() {
		return cookTime;
	}
	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}
	public String getRecipePhotoLink() {
		return recipePhotoLink;
	}
	public void setRecipePhotoLink(String recipePhotoLink) {
		this.recipePhotoLink = recipePhotoLink;
	}
	
	@JsonDeserialize(as=ArrayList.class, contentAs=HashMap.class)
	public ArrayList<HashMap<String, Object>> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<HashMap<String, Object>> ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAdditionalNote() {
		return additionalNote;
	}
	public void setAdditionalNote(String additionalNote) {
		this.additionalNote = additionalNote;
	}
	public String getYields() {
		return yields;
	}
	public void setYields(String yields) {
		this.yields = yields;
	}
	public HashMap<String, String> getNutritionFacts() {
		return nutritionFacts;
	}
	public void setNutritionFacts(HashMap<String, String> nutritionFacts) {
		this.nutritionFacts = nutritionFacts;
	}
	 
}

/*//To serialize ID
class ObjectID_Serializer extends JsonSerializer<ObjectId>{
	 
	@Override
	public void serialize(ObjectId objid, JsonGenerator jsongen, SerializerProvider provider) throws IOException, JsonProcessingException {
		
		if(objid == null ){
			jsongen.writeNull();
		}else{
			jsongen.writeString(objid.toString());
		}
		
	}
 
}*/