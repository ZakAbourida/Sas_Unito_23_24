package catering.persistence;

import catering.businesslogic.SummarySheet.Assignment;
import catering.businesslogic.SummarySheet.SheetEventReceiver;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.Turn.Turn;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.Cook;
import catering.businesslogic.user.User;

import java.util.List;

public class SheetPersistence implements SheetEventReceiver {

    @Override
    public void updateSheetCreated(SummarySheet sheet) {
        SummarySheet.saveNewSummarySheet(sheet);
    }

    @Override
    public void updateSheetDeleted(SummarySheet sheet) {
        SummarySheet.deleteSheet(sheet);
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
        SummarySheet.addPortion(asg, portion);
    }

    @Override
    public void updateAssignmentTime(Assignment asg, int time) {
        SummarySheet.addTime(asg, time);
    }

    @Override
    public void updateSummarySheetNotes(SummarySheet sheet, String note) {
        SummarySheet.addNote(sheet, note);
    }

    @Override
    public void updateCookInAssignment(Assignment asg, User cook) {
        SummarySheet.modifyCookInAssignment(asg, cook);
    }

    @Override
    public void updateTurnInAssignment(Assignment asg, Turn turn) {
        SummarySheet.modifyTurnInAssignment(asg, turn);
    }

    @Override
    public void updateItemModified(Recipe task, Assignment asg) {
        SummarySheet.modifyTaskInAssignment(asg, task);
    }

    @Override
    public void updateAssignmentDeleted(Assignment asg) {
        SummarySheet.deleteAssignment(asg);
    }

    @Override
    public void updateSectionsRearranged(SummarySheet sheet) {//SummarySheet.rearrangeSections(sheet);
    }
}