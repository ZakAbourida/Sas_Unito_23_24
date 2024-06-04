package catering.businesslogic.recipe;

import java.util.List;

public class RecipeBook extends ItemBook {
    private List<ItemBook> itemBooks;

    // Getter e setter per book
    public List<ItemBook> getBook() {
        return itemBooks;
    }

    public void setBook(List<ItemBook> book) {
        this.itemBooks = book;
    }

    public void add(ItemBook itemBook) {
    }
}