package catering.businesslogic.Turn;

import java.util.Date;

/**
 * Interface for receiving notifications about changes to turns.
 * <p>
 * Implementations of this interface can be used to respond to various events related to turns,
 * such as creation, deletion, assignment, and updates.
 * </p>
 */
public interface TurnEventReceiver {
    void updateTurnCreated(Turn turn);

    void updateTurnDeleted(Turn turn);

    void updateTurnAssigned(Turn turn);

    void updateTurnCompleted(Turn turn);

    void updateLocationAdded(Turn turn, String location);

    void updateDeadlineAdded(Turn turn, Date deadline);
}
