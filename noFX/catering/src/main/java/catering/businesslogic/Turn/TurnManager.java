package catering.businesslogic.Turn;


import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages operations related to turns and notifies event receivers of changes.
 * <p>
 * This class handles creating, deleting, and updating turns, as well as notifying registered event receivers about these changes.
 * </p>
 */
public class TurnManager {
    private TableTurns currentTable;
    private List<TurnEventReceiver> eventReceivers = new ArrayList<>();

    /**
     * <h2>ALL NOTIFY METHODS</h2>
     */

    /**
     * Notifies all registered event receivers that a turn has been created.
     *
     * @param turn The turn that was created.
     */
    public void notifyTurnCreated(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnCreated(turn);
        }
    }

    /**
     * Notifies all registered event receivers that a turn has been deleted.
     *
     * @param turn The turn that was deleted.
     */
    public void notifyTurnDeleted(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnDeleted(turn);
        }
    }

    /**
     * Notifies all registered event receivers that a turn has been assigned.
     *
     * @param turn The turn that was assigned.
     */
    public void notifyTurnAssigned(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnAssigned(turn);
        }
    }

    /**
     * Notifies all registered event receivers that a turn has been completed.
     *
     * @param turn The turn that was completed.
     */
    public void notifyTurnCompleted(Turn turn) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateTurnCompleted(turn);
        }
    }

    /**
     * Notifies all registered event receivers that a location has been added to a turn.
     *
     * @param turn     The turn to which the location was added.
     * @param location The location that was added.
     */
    public void notifyLocationAdded(Turn turn, String location) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateLocationAdded(turn, location);
        }
    }

    /**
     * Notifies all registered event receivers that a deadline has been added to a turn.
     *
     * @param turn     The turn to which the deadline was added.
     * @param deadline The deadline that was added.
     */
    public void notifyDeadlineAdded(Turn turn, Date deadline) {
        for (TurnEventReceiver receiver : eventReceivers) {
            receiver.updateDeadlineAdded(turn, deadline);
        }
    }

    /**
     * <h2>METHODS FOR MAIN OPERATIONS</h2>
     */

    /**
     * Creates a new turn and notifies event receivers of its creation.
     *
     * @param service The service information for the new turn.
     * @return The newly created turn.
     */
    public Turn createTurn(ServiceInfo service) {
        Turn turn = new Turn();
        notifyTurnCreated(turn);
        return turn;
    }

    /**
     * Deletes a turn and notifies event receivers of its deletion.
     *
     * @param turn The turn to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteTurn(Turn turn) {
        notifyTurnDeleted(turn);
        return true;
    }

    /**
     * Adds a group to a turn and notifies event receivers of the assignment.
     *
     * @param group The group to add.
     * @param turn  The turn to which the group is added.
     */
    public void addGroupToTurn(Grouping group, Turn turn) {
        group.addTurn(turn);
        notifyTurnAssigned(turn);
    }

    /**
     * Adds a location to a turn and notifies event receivers of the update.
     *
     * @param turn     The turn to update.
     * @param location The location to add.
     */
    public void addLocationToTurn(Turn turn, String location) {
        turn.setLocation(location);
        notifyLocationAdded(turn, location);
    }

    /**
     * Adds a deadline to a turn and notifies event receivers of the update.
     *
     * @param turn     The turn to update.
     * @param deadline The deadline to add.
     */
    public void addDeadlineToTurn(RoomTurn turn, Date deadline) {
        turn.setDeadline(deadline);
        notifyDeadlineAdded(turn, deadline);
    }

    /**
     * Assigns a user to a turn and notifies event receivers of the assignment.
     *
     * @param turn The turn to update.
     * @param user The user to assign.
     */
    public void assignTurn(Turn turn, User user) {
        turn.assign(user);
        notifyTurnAssigned(turn);
    }

    /**
     * <h2>GETTER AND SETTER</h2>
     */

    /**
     * Returns a list of events.
     *
     * @return An empty list of events.
     */
    public List<EventInfo> getEvents() {
        return new ArrayList<>();
    }
}
