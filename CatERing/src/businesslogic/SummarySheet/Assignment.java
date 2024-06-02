package businesslogic.SummarySheet;

import businesslogic.Turn.Turn;
import businesslogic.recipe.ItemBook;
import businesslogic.user.User;

public class Assignment {
    private User cook;
    private Turn turn;
    private ItemBook itemBook;

    public Assignment(User cook, Turn turn, ItemBook itemBook) {
        this.cook = cook;
        this.turn = turn;
        this.itemBook = itemBook;
    }

    // Getter e setter per cook, turn e itemBook

    public static Assignment create(User cook, Turn turn, ItemBook itemBook) {
        return new Assignment(cook, turn, itemBook);
    }
}
