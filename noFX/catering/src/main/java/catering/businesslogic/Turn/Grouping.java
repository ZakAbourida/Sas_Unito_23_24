package catering.businesslogic.Turn;

import java.util.ArrayList;
import java.util.List;

public class Grouping {
    private List<Turn> turns = new ArrayList<>();

    public void addTurn(Turn turn) {
        turns.add(turn);
    }

}