package catering.businesslogic.SummarySheet;



import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.recipe.Recipe;

import java.sql.Time;
import java.util.List;

public interface SheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetDeleted(SummarySheet sheet);
    void updateItemAdded(SummarySheet sheet, ItemBook item);

    void updateItemAdded(SummarySheet sheet, List<Recipe> items);

    void updateAssignmentCreated(SummarySheet sheet, Assignment assignment);
    void updateExtraTaskRearranged(SummarySheet currentSheet, Recipe item);
    void updateAssignmentPortion(Assignment asg, int portion);
    void updateAssignmentTime(Assignment asg, int time);
    void updateSummarySheetNotes(SummarySheet currentSheet, String note);

    void updateCookInAssignment(Assignment asg);

    void updateTurnInAssignment(Assignment asg);

    void updateItemModified(ItemBook task, Assignment asg);
}
