package businesslogic.SummarySheet;

import businesslogic.recipe.ItemBook;

public interface SheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetDeleted(SummarySheet sheet);
    void updateItemAdded(SummarySheet sheet, ItemBook item);
    void updateAssignmentCreated(SummarySheet sheet, Assignment assignment);

    void updateExtraTaskRearranged(SummarySheet currentSheet, ItemBook item);
}
