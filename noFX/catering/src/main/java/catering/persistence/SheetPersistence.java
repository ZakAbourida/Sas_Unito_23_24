package catering.persistence;

import catering.businesslogic.SummarySheet.Assignment;
import catering.businesslogic.SummarySheet.SheetEventReceiver;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.recipe.ItemBook;

import java.sql.Time;

public class SheetPersistence implements SheetEventReceiver {

    @Override
    public void updateSheetCreated(SummarySheet sheet) {
    }

    @Override
    public void updateSheetDeleted(SummarySheet sheet) {
    }

    @Override
    public void updateItemAdded(SummarySheet sheet, ItemBook item) {
    }

    @Override
    public void updateAssignmentCreated(SummarySheet sheet, Assignment assignment) {
    }

    @Override
    public void updateExtraTaskRearranged(SummarySheet currentSheet, ItemBook item) {
    }

    @Override
    public void updateAssignmentPortion(Assignment asg, int portion) {
    }

    @Override
    public void updateAssignmentTime(Assignment asg, Time time) {
    }

    @Override
    public void updateSummarySheetNotes(SummarySheet sheet, String notes) {
    }

}
