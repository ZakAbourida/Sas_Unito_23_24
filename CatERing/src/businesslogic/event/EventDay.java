package businesslogic.event;

import java.util.Date;
import java.util.List;

public class EventDay {
    private Date date;
    private List<Service> services;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
