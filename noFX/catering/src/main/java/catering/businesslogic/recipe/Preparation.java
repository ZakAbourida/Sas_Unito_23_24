package catering.businesslogic.recipe;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Preparation extends ItemBook {
    private static Map<Integer, Preparation> all = new HashMap<>();

    private int id;
    private String name;

    private Preparation() {
    }

    public Preparation(String name) {
        id = 0;
        this.name = name;
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

    // STATIC METHODS FOR PERSISTENCE

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

    public static ArrayList<Preparation> getAllPreparations() {
        return new ArrayList<Preparation>(all.values());
    }

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

    public void setId(int id) {
        this.id = id;
    }
}
