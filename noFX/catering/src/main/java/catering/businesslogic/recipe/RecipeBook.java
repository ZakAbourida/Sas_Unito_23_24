package catering.businesslogic.recipe;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a recipe book, which contains a collection of {@link ItemBook} items, including recipes and preparations.
 */
public class RecipeBook extends ItemBook {

    private static Map<Integer, RecipeBook> all = new HashMap<>();
    private List<ItemBook> itemBooks;
    private int id;

    /**
     * Constructs a new {@code RecipeBook} with an empty list of item books.
     */
    public RecipeBook() {
        this.itemBooks = new ArrayList<>();
    }

    /**
     * Loads all recipe books from the database.
     *
     * @return A list of all {@code RecipeBook} instances.
     */
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

    /**
     * Loads a specific recipe book by its ID.
     *
     * @param id The ID of the recipe book to load.
     * @return The {@code RecipeBook} with the specified ID.
     */
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

    /**
     * Loads items for a given recipe book.
     *
     * @param book The {@code RecipeBook} for which to load items.
     */
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

    /**
     * Retrieves all recipe books.
     *
     * @return A list of all {@code RecipeBook} instances.
     */
    public static ArrayList<RecipeBook> getAllRecipeBooks() {
        return new ArrayList<>(all.values());
    }

    /**
     * Adds an {@code ItemBook} to this {@code RecipeBook}.
     *
     * @param itemBook The {@code ItemBook} to be added.
     */
    public void addItem(ItemBook itemBook) {
        this.itemBooks.add(itemBook);
    }

    /**
     * <h2>GETTER AND SETTER</h2>
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemBook> getItemBooks() {
        return itemBooks;
    }

    public void setItemBooks(List<ItemBook> itemBooks) {
        this.itemBooks = itemBooks;
    }
}