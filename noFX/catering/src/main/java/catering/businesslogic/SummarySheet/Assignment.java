package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.user.User;

import java.sql.Time;

public class Assignment {
    private User cook;
    private Turn turn;
    private ItemBook itemBook;
    private int portion;
    private Time startTime;

    public Assignment(User cook, Turn turn, ItemBook itemBook) {
        this.cook = cook;
        this.turn = turn;
        this.itemBook = itemBook;
    }

    public static Assignment create(User cook, Turn turn, ItemBook itemBook) {
        return new Assignment(cook, turn, itemBook);
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setTime(Time time) {
        this.startTime = time;
    }
}
