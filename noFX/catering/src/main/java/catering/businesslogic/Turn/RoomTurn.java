package catering.businesslogic.Turn;


import catering.businesslogic.event.ServiceInfo;

import java.util.Date;

/**
 * Represents a turn that has an associated deadline.
 */
public class RoomTurn extends Turn {
    private Date deadline;

    /**
     * Constructs a new {@code RoomTurn} with the specified service information.
     *
     * @param service The service information associated with this turn.
     */
    public RoomTurn(ServiceInfo service) {
        super();
    }

    /**
     * Returns the deadline associated with this turn.
     *
     * @return The deadline for this turn.
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline for this turn.
     *
     * @param deadline The deadline to be set.
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
