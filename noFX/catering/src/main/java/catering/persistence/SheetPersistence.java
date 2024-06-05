package catering.persistence;

import catering.businesslogic.SummarySheet.Assignment;
import catering.businesslogic.SummarySheet.SheetEventReceiver;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.recipe.Recipe;

import java.sql.Time;
import java.util.List;

public class SheetPersistence implements SheetEventReceiver {

    @Override
    public void updateSheetCreated(SummarySheet sheet){
        SummarySheet.saveNewSummarySheet(sheet);
    }

    @Override
    public void updateSheetDeleted(SummarySheet sheet) {
        SummarySheet.deleteSheet(sheet);
    }

    @Override
    public void updateItemAdded(SummarySheet sheet, ItemBook item) {

    }

    @Override
    public void updateItemAdded(SummarySheet sheet, List<Recipe> items) {
        SummarySheet.addItem(sheet, items);
    }

    @Override
    public void updateAssignmentCreated(SummarySheet sheet, Assignment assignment) {
        SummarySheet.saveNewAssignment(sheet, assignment);
    }

    @Override
    public void updateExtraTaskRearranged(SummarySheet sheet, Recipe item) {
        // Implementazione della persistenza per la riorganizzazione degli extra task
        // Non fornita nel contesto
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

    @Override
    public void updateCookInAssignment(Assignment asg) {

    }

    @Override
    public void updateTurnInAssignment(Assignment asg) {

    }

    @Override
    public void updateItemModified(ItemBook task, Assignment asg) {

    }
}