package catering.businesslogic.Turn;


import catering.businesslogic.event.ServiceInfo;

import java.util.Date;

public class RoomTurn extends Turn {
    private Date deadline;

    public RoomTurn(ServiceInfo service) {
        super();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
