package catering.businesslogic.SummarySheet;



import catering.businesslogic.recipe.ItemBook;

import java.sql.SQLException;
import java.sql.Time;

public interface SheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetDeleted(SummarySheet sheet);
    void updateItemAdded(SummarySheet sheet, ItemBook item);
    void updateAssignmentCreated(SummarySheet sheet, Assignment assignment);
    void updateExtraTaskRearranged(SummarySheet currentSheet, ItemBook item);
    void updateAssignmentPortion(Assignment asg, int portion);
    void updateAssignmentTime(Assignment asg, Time time);
    void updateSummarySheetNotes(SummarySheet currentSheet, String note);

}
