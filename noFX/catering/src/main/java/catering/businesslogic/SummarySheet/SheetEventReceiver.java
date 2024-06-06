package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.Cook;

import java.util.List;

public interface SheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetDeleted(SummarySheet sheet);
    void updateItemAdded(SummarySheet sheet, List<Recipe> items);

    void updateAssignmentCreated(SummarySheet sheet, Assignment assignment);
    void updateExtraTaskRearranged(SummarySheet currentSheet, Recipe item);
    void updateAssignmentPortion(Assignment asg, int portion);
    void updateAssignmentTime(Assignment asg, int time);
    void updateSummarySheetNotes(SummarySheet currentSheet, String note);

    void updateCookInAssignment(Assignment asg, Cook cook);

    void updateTurnInAssignment(Assignment asg, Turn turn);

    void updateItemModified(Recipe task, Assignment asg);

    void updateAssignmentDeleted(Assignment asg);

    void updateSectionsRearranged(SummarySheet sheet);
}
