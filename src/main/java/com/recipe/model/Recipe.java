package com.recipe.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
	//@JsonDeserialize(as=ArrayList.class, contentAs=String.class)
	String name;
	String difficultyLevel;
	String recipeCategory;
	String cuisine;
	String prepTime;
	String cookTime;
	String recipePhotoLink;
	String recipeVideoLink;
	@JsonDeserialize(as=ArrayList.class, contentAs=HashMap.class)
	ArrayList<HashMap<String,Object>> ingredients;
	String direction;
	String additionalNote;
	String yields;
	HashMap<String,String> nutritionFacts;
	
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
	public String getRecipeVideoLink() {
		return recipeVideoLink;
	}
	public void setRecipeVideoLink(String recipeVideoLink) {
		this.recipeVideoLink = recipeVideoLink;
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