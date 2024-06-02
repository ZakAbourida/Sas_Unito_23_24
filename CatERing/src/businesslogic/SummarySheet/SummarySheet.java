package businesslogic.SummarySheet;

import businesslogic.Turn.Turn;
import businesslogic.event.Service;
import businesslogic.recipe.ItemBook;
import businesslogic.user.Cook;
import businesslogic.user.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SummarySheet {
    private String note;
    private User owner;
    private List<Assignment> assignments;
    private List<ItemBook> extraTask;


    public void setNote(String note) {
        this.note = note;
    }

    public SummarySheet(User owner) {
        this.owner = owner;
        this.assignments = new ArrayList<>();
        this.extraTask = new ArrayList<>();

    }

    public boolean isInProgress() {
        // Implementazione della logica per verificare se è in corso
        return false;
    }

    public boolean isOwner(User user) {
        // Implementazione della logica per verificare se l'utente è il proprietario
        return false;
    }

    public void addExtraTask(ItemBook itemBook) {
        this.extraTask.add(itemBook);
    }

    public void moveRecipePreparation(ItemBook item1, ItemBook item2) {
        int index1 = extraTask.indexOf(item1);
        int index2 = extraTask.indexOf(item2);

        if (index1 == -1 || index2 == -1) {
            return; // Uno o entrambi gli elementi non esistono nella lista
        }

        // Scambia gli elementi
        extraTask.set(index1, item2);
        extraTask.set(index2, item1);
    }

    public Assignment createAssignment(Cook cook, Turn turn, ItemBook itemBook) {
        Assignment asg = new Assignment(cook, turn, itemBook);
        assignments.add(asg);
        return asg;
    }

    public void assignPortion(int portion, Assignment asg) {
        asg.setPortion(portion);
    }

    public void assignTime(Time time, Assignment asg) {
        asg.setTime(time);
    }
}







