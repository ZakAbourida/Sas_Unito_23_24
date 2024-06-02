package businesslogic.recipe;

import java.util.List;

public class RecipeBookManager {
    private RecipeBook recipeBook;

    // Metodi per inviare eventi (event sender methods)
    // ...

    // Metodi operativi
    public RecipeBook getItemBook() {
        return recipeBook;
    }

    public void addItemBook(ItemBook itemBook) {
        this.recipeBook.add(itemBook);
    }

    // Altri metodi operativi
}
