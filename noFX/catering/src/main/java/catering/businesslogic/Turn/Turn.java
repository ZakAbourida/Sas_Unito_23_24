package catering.businesslogic.Turn;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a turn, which includes details such as date, start and end times, location, and associated service information.
 */
public class Turn {
    private int id;
    private Date date;
    private Time start;
    private Time end;
    private String location;
    private ServiceInfo service;

    public Turn() {
    }

    /**
     * <h2>STATIC METHODS FOR PERSISTENCE</h2>
     */

    /**
     * Loads a turn from the database based on its ID.
     *
     * @param id The ID of the turn to be loaded.
     * @return The loaded turn.
     */
    public static Turn loadTurnById(int id) {
        Turn turn = new Turn();
        String query = "SELECT * FROM turn WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                turn.setId(rs.getInt("id"));
                turn.setDate(rs.getDate("date"));
                turn.setStart(rs.getTime("start"));
                turn.setEnd(rs.getTime("end"));
                turn.setLocation(rs.getString("location"));
                turn.setService(ServiceInfo.loadServiceInfoById(rs.getInt("service")));
            }
        });
        return turn;
    }

    /**
     * Loads all turns associated with a specific service.
     *
     * @param serviceId The ID of the service for which turns are to be loaded.
     * @return A list of turns associated with the given service ID.
     */
    public static List<Turn> loadAllTurnsForService(int serviceId) {
        List<Turn> turns = new ArrayList<>();
        String query = "SELECT * FROM turn WHERE service = " + serviceId;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Turn turn = new Turn();
                turn.setId(rs.getInt("id"));
                turn.setDate(rs.getDate("date"));
                turn.setStart(rs.getTime("start"));
                turn.setEnd(rs.getTime("end"));
                turn.setLocation(rs.getString("location"));
                turn.setService(ServiceInfo.loadServiceInfoById(rs.getInt("service")));
                turns.add(turn);
            }
        });
        return turns;
    }

    /**
     * Loads all turns from the database.
     *
     * @return A list of all turns.
     */
    public static List<Turn> loadAllTurns() {
        List<Turn> turns = new ArrayList<>();
        String query = "SELECT * FROM turn";
        PersistenceManager.executeQuery(query, rs -> {
            Turn turn = new Turn();
            turn.setId(rs.getInt("id"));
            turn.setDate(rs.getDate("date"));
            turn.setStart(rs.getTime("start"));
            turn.setEnd(rs.getTime("end"));
            turn.setLocation(rs.getString("location"));
            turn.setService(ServiceInfo.loadServiceInfoById(rs.getInt("service")));
            turns.add(turn);
        });
        return turns;
    }

    /**
     * <h2>METHODS FOR MAIN OPERATIONS</h2>
     */

    /**
     * Returns a string representation of the turn, including its date, start and end times, and location.
     *
     * @return A string describing the turn.
     */
    @Override
    public String toString() {
        return "Date: " + date +
                ", Start: " + start +
                ", End: " + end +
                ", Location: " + location;
    }

    /**
     * Assigns a user to the turn.
     *
     * @param user The user to be assigned to the turn.
     */
    public void assign(User user) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ServiceInfo getService() {
        return service;
    }

    public void setService(ServiceInfo service) {
        this.service = service;
    }

}