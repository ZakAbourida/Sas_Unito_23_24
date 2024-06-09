package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Assignment {
    private int id;
    private User cook;
    private Turn turn;
    private Recipe item;
    private int portion;
    private int time;

    public Assignment(User cook, Turn turn, Recipe item) {
        this.cook = cook;
        this.turn = turn;
        this.item = item;
        this.portion = 0;
        this.time = 0;
    }

    /**
     * <h2>METHODS FOR MAIN OPERATIONS</h2>
     */

    @Override
    public String toString() {
        return "Assignment: " +
                "Turn: " + (turn != null ? turn.toString() : "N/A") +
                ", Cook: " + (cook != null ? cook.getUserName() : "N/A") +
                ", Recipe: " + (item != null ? item.getName() : "N/A") +
                ", Portion: " + portion +
                ", Time: " + time;
    }

    /**
     * <h2>STATIC METHODS FOR PERSISTENCE</h2>
     */

    public static void loadAssignments(int sheetId, List<Assignment> assignments) {
        String query = "SELECT * FROM assignment WHERE sheet = " + sheetId;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Turn turn = Turn.loadTurnById(rs.getInt("turn"));
                User cook = User.loadUserById(rs.getInt("cook"));
                Recipe recipe = Recipe.loadRecipeById(rs.getInt("recipe"));
                int portion = rs.getInt("portion");
                int time = rs.getInt("time");

                if (turn != null && cook != null && recipe != null) {
                    Assignment assignment = new Assignment(cook, turn, recipe);
                    assignment.setPortion(portion);
                    assignment.setTime(time);
                    assignment.setId(rs.getInt("id"));
                    assignments.add(assignment);
                }
            }
        });
    }

    /**
     * <h2>GETTER AND SETTER</h2>
     */

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setTime(int startTime) {
        this.time = startTime;
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

    public void setTask(Recipe item) {
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

    public Recipe getRecipe() {
        return item;
    }

    public int getPortion() {
        return portion;
    }

    public int getTime() {
        return time;
    }
}
