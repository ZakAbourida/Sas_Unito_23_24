package catering.businesslogic.Turn;



import catering.businesslogic.event.ServiceInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableTurns {
    private List<Turn> turns = new ArrayList<>();

    public Turn createTurn(ServiceInfo service) {
        Turn turn = new Turn();
        turns.add(turn);
        return turn;
    }

    public boolean deleteTurn(Turn turn) {
        return turns.remove(turn);
    }

    public void addGroupToTurn(Grouping group, Turn turn) {
        group.addTurn(turn);
    }

    public void addLocationToTurn(Turn turn, String location) {
        turn.setLocation(location);
    }

    public void addDeadlineToTurn(RoomTurn turn, Date deadline) {
        turn.setDeadline(deadline);
    }
}