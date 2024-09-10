package catering.businesslogic.Turn;


import catering.businesslogic.event.ServiceInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages a collection of turns and provides methods to create, delete, and modify them.
 */
public class TableTurns {
    private List<Turn> turns = new ArrayList<>();

    /**
     * Creates a new turn associated with a specific service and adds it to the list of turns.
     *
     * @param service The service information associated with the new turn.
     * @return The created turn.
     */
    public Turn createTurn(ServiceInfo service) {
        Turn turn = new Turn();
        turns.add(turn);
        return turn;
    }

    /**
     * Deletes a specified turn from the list of turns.
     *
     * @param turn The turn to be deleted.
     * @return {@code true} if the turn was successfully removed, {@code false} otherwise.
     */
    public boolean deleteTurn(Turn turn) {
        return turns.remove(turn);
    }

    /**
     * Adds a group to a specific turn.
     *
     * @param group The group to be added.
     * @param turn  The turn to which the group will be added.
     */
    public void addGroupToTurn(Grouping group, Turn turn) {
        group.addTurn(turn);
    }

    /**
     * Adds a location to a specific turn.
     *
     * @param turn     The turn to which the location will be added.
     * @param location The location to be set for the turn.
     */
    public void addLocationToTurn(Turn turn, String location) {
        turn.setLocation(location);
    }

    /**
     * Adds a deadline to a specific turn.
     *
     * @param turn     The turn to which the deadline will be added.
     * @param deadline The deadline to be set for the turn.
     */
    public void addDeadlineToTurn(RoomTurn turn, Date deadline) {
        turn.setDeadline(deadline);
    }
}