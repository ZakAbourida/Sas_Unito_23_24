package catering.businesslogic.Turn;



import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

public class Turn {
    private int id;
    private Date date;
    private Time start;
    private Time end;
    private String location;
    private ServiceInfo service;

    public Turn() {

    }

    public static Turn loadTurnById(int id) {
        Turn turn = new Turn();
        String query = "SELECT * FROM turn WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    turn.setId(rs.getInt("id"));
                    // Imposta altri attributi se necessario
                }
            }
        });
        return turn;
    }

    public void setLocation(String location) {
    }

    public void assign(User user) {
    }

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

    public ServiceInfo getService() {
        return service;
    }

    public void setService(ServiceInfo service) {
        this.service = service;
    }
}
