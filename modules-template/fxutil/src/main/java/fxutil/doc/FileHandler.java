package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    
    public void writeRecipeToFile(String filename, Cookbook cookbook) throws IOException {
        int index = 0;
		try (FileWriter fileWriter = new FileWriter(filename)) {
			for (Recipe recipe : cookbook.getRecipes()) {
				String newLine = recipe.getName() + ";" + recipe.getPortions() + "/n";
				for (Ingredient ingredient : recipe.getIngredients()) {
					newLine += ingredient.getName() + ";" + ingredient.getAmount() + ";" + ingredient.getUnit() + "&";
				}

				newLine += "/n" + recipe.getDescription();
	
				if (index < cookbook.getRecipes().size()) {
					newLine += "\n";

				}
				fileWriter.write(newLine);
				index++;
			}
			fileWriter.close();
		}
	}

	public void readRecipesFromFile(String filename, Cookbook cookbook) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String[] recipeInformation = scanner.nextLine().split(";");
				String recipeTitle = recipeInformation[0];
				String portions = recipeInformation[1];
				int intPortions = Integer.parseInt(portions);
				Recipe newRecipe = new Recipe(recipeTitle, intPortions);

				String[] ingredients = scanner.nextLine().split("&");
				for (String ing : ingredients) {
					String[] ingredientElements = ing.split(";");
					
					Ingredient newIngredient = new Ingredient(ingredientElements[0],
					    Double.parseDouble(ingredientElements[1]), ingredientElements[2]);
					newRecipe.addIngredient(newIngredient);
				}

				String description = scanner.nextLine();
				newRecipe.setDescription(description);
				
				cookbook.addRecipe(newRecipe);
			}
			scanner.close();
		}
	}

	

	
    
}