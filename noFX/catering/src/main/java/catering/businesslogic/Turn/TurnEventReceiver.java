package catering.businesslogic.Turn;

import java.util.Date;

public interface TurnEventReceiver {
    void updateTurnCreated(Turn turn);
    void updateTurnDeleted(Turn turn);
    void updateTurnAssigned(Turn turn);
    void updateTurnCompleted(Turn turn);
    void updateLocationAdded(Turn turn, String location);
    void updateDeadlineAdded(Turn turn, Date deadline);
}
