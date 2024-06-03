package catering.businesslogic.Turn;



import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TurnManager {
    private TableTurns currentTable;
    private List<TurnEventReceiver> eventReceivers = new ArrayList<>();

    // Event sender methods
    public void notifyTurnCreated(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnCreated(turn);
        }
    }

    public void notifyTurnDeleted(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnDeleted(turn);
        }
    }

    public void notifyTurnAssigned(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnAssigned(turn);
        }
    }

    public void notifyTurnCompleted(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnCompleted(turn);
        }
    }

    public void notifyLocationAdded(Turn turn, String location) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateLocationAdded(turn, location);
        }
    }

    public void notifyDeadlineAdded(Turn turn, Date deadline) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateDeadlineAdded(turn, deadline);
        }
    }
    // Operation methods
    public Turn createTurn(ServiceInfo service) {
        Turn turn = new Turn(service);
        notifyTurnCreated(turn);
        return turn;
    }

    public boolean deleteTurn(Turn turn) {
        notifyTurnDeleted(turn);
        return true;
    }

    public void addGroupToTurn(Grouping group, Turn turn) {
        group.addTurn(turn);
        notifyTurnAssigned(turn);
    }

    public void addLocationToTurn(Turn turn, String location) {
        turn.setLocation(location);
        notifyLocationAdded(turn, location);
    }

    public void addDeadlineToTurn(RoomTurn turn, Date deadline) {
        turn.setDeadline(deadline);
        notifyDeadlineAdded(turn, deadline);
    }

    public void assignTurn(Turn turn, User user) {
        turn.assign(user);
        notifyTurnAssigned(turn);
    }

    public List<EventInfo> getEvents() {
        // Implementazione della logica per ottenere gli eventi
        return new ArrayList<>();
    }
}
