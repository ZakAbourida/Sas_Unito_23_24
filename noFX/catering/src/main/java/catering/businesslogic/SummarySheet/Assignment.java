package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;

import java.sql.Time;

public class Assignment {
    private int id;
    private User cook;
    private Turn turn;
    private ItemBook item;
    private int portion;
    private Time startTime;

    public Assignment(User cook, Turn turn, ItemBook item) {
        this.cook = cook;
        this.turn = turn;
        this.item = item;
    }


    @Override
    public String toString() {
        return "Assignment ID: " + id + ", Cook: " + cook.getUserName() + ", Turn: " + turn.toString() + ", Item: " + item.toString() + ", Portion: " + portion + ", Time: " + startTime;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void setTask(ItemBook item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public User getCook() {
        return cook;
    }

    public Turn getTurn() {
        return turn;
    }

    public ItemBook getRecipe() {
        return item;
    }

    public int getPortion() {
        return portion;
    }

    public Time getTime() {
        return startTime;
    }
}
