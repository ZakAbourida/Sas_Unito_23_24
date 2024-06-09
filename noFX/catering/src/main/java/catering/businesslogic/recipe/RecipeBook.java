package catering.businesslogic.recipe;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeBook extends ItemBook {
    private List<ItemBook> itemBooks;

    public RecipeBook() {
        this.itemBooks = new ArrayList<>();
    }

    // Getter e setter per itemBooks
    public List<ItemBook> getItemBooks() {
        return itemBooks;
    }

    public void setItemBooks(List<ItemBook> itemBooks) {
        this.itemBooks = itemBooks;
    }

    public void addItem(ItemBook itemBook) {
        this.itemBooks.add(itemBook);
    }

    // STATIC METHODS FOR PERSISTENCE

    private static Map<Integer, RecipeBook> all = new HashMap<>();

    public static ArrayList<RecipeBook> loadAllRecipeBooks() {
        String query = "SELECT * FROM RecipeBooks";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (!all.containsKey(id)) {
                    RecipeBook book = new RecipeBook();
                    book.setId(id);
                    all.put(id, book);
                }
            }
        });

        for (RecipeBook book : all.values()) {
            loadItemsForRecipeBook(book);
        }

        return new ArrayList<>(all.values());
    }

    public static RecipeBook loadRecipeBookById(int id) {
        if (all.containsKey(id)) {
            return all.get(id);
        }

        RecipeBook book = new RecipeBook();
        String query = "SELECT * FROM RecipeBooks WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    book.setId(rs.getInt("id"));
                }
            }
        });

        loadItemsForRecipeBook(book);
        all.put(id, book);
        return book;
    }

    private static void loadItemsForRecipeBook(RecipeBook book) {
        String query = "SELECT * FROM RecipeBookItems WHERE book_id = " + book.getId();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    int itemId = rs.getInt("item_id");
                    String itemType = rs.getString("item_type");
                    ItemBook item = null;
                    if ("Recipe".equals(itemType)) {
                        item = Recipe.loadRecipeById(itemId);
                    } else if ("Preparation".equals(itemType)) {
                        item = Preparation.loadPreparationById(itemId);
                    }
                    if (item != null) {
                        book.addItem(item);
                    }
                }
            }
        });
    }

    public static ArrayList<RecipeBook> getAllRecipeBooks() {
        return new ArrayList<>(all.values());
    }

    // Attributi e metodi specifici per RecipeBook
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}