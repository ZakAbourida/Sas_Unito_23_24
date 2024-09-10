package catering.businesslogic.recipe;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Represents a preparation with an ID and name. Provides methods to load preparations from the database and manage them.
 */
public class Preparation extends ItemBook {
    private static Map<Integer, Preparation> all = new HashMap<>();

    private int id;
    private String name;

    /**
     * Constructs a new {@code Preparation} with no initial values.
     */
    private Preparation() {
    }

    /**
     * Constructs a new {@code Preparation} with the specified name.
     *
     * @param name The name of the preparation.
     */
    public Preparation(String name) {
        id = 0;
        this.name = name;
    }


    /**
     * <h2>STATIC METHODS FOR PERSISTENCE</h2>
     */

    /**
     * Loads all preparations from the database.
     *
     * @return A list of all {@code Preparation} instances, sorted by name.
     */
    public static ArrayList<Preparation> loadAllPreparations() {
        String query = "SELECT * FROM Preparations";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Preparation prep = all.get(id);
                    prep.name = rs.getString("name");
                } else {
                    Preparation prep = new Preparation(rs.getString("name"));
                    prep.id = id;
                    all.put(prep.id, prep);
                }
            }
        });
        ArrayList<Preparation> ret = new ArrayList<Preparation>(all.values());
        Collections.sort(ret, new Comparator<Preparation>() {
            @Override
            public int compare(Preparation o1, Preparation o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    /**
     * Loads a specific preparation by its ID.
     *
     * @param id The ID of the preparation to load.
     * @return The {@code Preparation} with the specified ID, or {@code null} if not found.
     */
    public static Preparation loadPreparationById(int id) {
        Preparation preparation = new Preparation();
        String query = "SELECT * FROM Preparations WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    preparation.setId(rs.getInt("id"));
                    // Imposta altri attributi se necessario
                }
            }
        });
        return preparation;
    }

    /**
     * <h2>GETTER AND SETTER</h2>
     */

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Preparation> getAllPreparations() {
        return new ArrayList<Preparation>(all.values());
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }
}
