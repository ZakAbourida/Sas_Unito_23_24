package catering.businesslogic.Turn;



import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.user.User;

import java.sql.Time;
import java.util.Date;

public class Turn {
    private Date date;
    private Time start;
    private Time end;
    private String location;
    private ServiceInfo service;

    public Turn(ServiceInfo service) {

    }

    public void setLocation(String location) {
    }

    public void assign(User user) {
    }
    // Getter e setter per date, start, end, assigned, completed, location e deadline
}
