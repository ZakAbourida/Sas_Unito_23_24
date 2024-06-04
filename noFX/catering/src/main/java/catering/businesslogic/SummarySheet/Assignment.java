package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.user.User;

import java.sql.Time;

public class Assignment {
    private int id;
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

    @Override
    public String toString() {
        return "Assignment ID: " + id + ", Cook: " + cook.getUserName() + ", Turn: " + turn.toString() + ", Item: " + itemBook.toString() + ", Portion: " + portion + ", Time: " + startTime;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setTime(Time startTime) {
        this.startTime = startTime;
    }

    public User getCook() {
        return cook;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public int getId() {
        return id;
    }

    public Turn getTurn() {
        return turn;
    }

    public ItemBook getItemBook() {
        return itemBook;
    }

    public int getPortion() {
        return portion;
    }

    public Time getStartTime() {
        return startTime;
    }
}
