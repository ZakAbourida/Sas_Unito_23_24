package businesslogic.Turn;

import businesslogic.event.Service;
import java.util.Date;

public class RoomTurn extends Turn {
    private Date deadline;

    public RoomTurn(Service service) {
        super(service);
    }

    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
