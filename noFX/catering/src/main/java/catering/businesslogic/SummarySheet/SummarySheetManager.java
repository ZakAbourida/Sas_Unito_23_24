package catering.businesslogic.SummarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.Turn.Turn;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SummarySheetManager {
    private SummarySheet currentSheet;

    private List<SheetEventReceiver> eventReceivers = new ArrayList<>();

    public void addReceiver(SheetEventReceiver receiver) {
        eventReceivers.add(receiver);
    }

    public void removeReceiver(SheetEventReceiver receiver) {
        eventReceivers.remove(receiver);
    }

    public void notifySheetCreated(SummarySheet sheet) throws SQLException {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateSheetCreated(sheet);
        }
    }

    public void notifySheetDeleted(SummarySheet sheet) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateSheetDeleted(sheet);
        }
    }

    private void notifyAssignmentDeleted(Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateAssignmentDeleted(asg);
        }
    }

    private void notifyItemAdded(List<Recipe> items) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateItemAdded(currentSheet, items);
        }
    }

    private void notifyItemsRearranged(Recipe item) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateExtraTaskRearranged(currentSheet, item);
        }
    }

    private void notifyAssignmentCreated(SummarySheet sheet,Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateAssignmentCreated(sheet, asg);
        }
    }

    private void notifyPortionAdded(Assignment asg, int portion) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateAssignmentPortion(asg, portion);
        }
    }

    private void notifyTimeAdded(Assignment asg, int time) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateAssignmentTime(asg, time);
        }
    }

    private void notifyNoteAdded(String note) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateSummarySheetNotes(currentSheet, note);
        }
    }

    private void notifyCookModified(Assignment asg, User cook) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateCookInAssignment(asg, cook);
        }
    }

    private void notifyNewTurnInAssignment(Assignment asg, Turn turn) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateTurnInAssignment(asg, turn);
        }
    }

    private void notifyItemModified(Recipe task, Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateItemModified(task, asg);
        }
    }

    private void notifyItemsRearanged(SummarySheet sheet) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateSectionsRearranged(sheet);
        }
    }

    public SummarySheet createSummarySheet(ServiceInfo service) throws SQLException, UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (user.isChef() && service.isAssignedChef(user) && service.isAssignedMenu()) {
            SummarySheet sheet = new SummarySheet(user, service);
            SummarySheet.saveNewSummarySheet(sheet);
            service.setSheet(sheet);
            setCurrentSheet(sheet);
            notifySheetCreated(sheet);
            return sheet;
        } else {
            throw new UseCaseLogicException();
        }
    }

    public void deleteSheet(SummarySheet sheet) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (sheet.isOwner(user) && !sheet.isInProgress()) {
            notifySheetDeleted(sheet);
        }
    }

    public void deleteAssignment(Assignment asg) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user) && !currentSheet.isInProgress()) {
            notifyAssignmentDeleted(asg);
        }
    }

    public boolean modifySheet(SummarySheet sheet) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (sheet.isOwner(user) && !sheet.isInProgress()) {
            return sheet.isOwner(user);
        } else {
            throw new UseCaseLogicException();
        }
    }

    public void addPreparationOrRecipe(Recipe item) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            List<Recipe> items = new ArrayList<>();
            items.add(item);
            notifyItemAdded(items);
        } else {
            throw new UseCaseLogicException();
        }
    }

    public void moveRecipePreparation(Recipe item, int pos) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.moveRecipePreparation(item, pos);
            notifyItemsRearanged(currentSheet);
        } else {
            throw new UseCaseLogicException();
        }
    }

    public void createAssignment(SummarySheet sheet, User cook, Turn turn, Recipe item, Integer portion, Integer time) throws UseCaseLogicException {
        if (sheet == null || sheet.getId() == 0) {
            throw new IllegalArgumentException("SummarySheet cannot be null and must have a valid ID");
        }
        if (portion == null) {
            portion = 0;
        }
        if (time == null) {
            time = 0;
        }
        Assignment assignment = sheet.createAssignment(cook, turn, item);
        sheet.assignPortion(portion, assignment);
        sheet.assignTime(time, assignment);
        notifyAssignmentCreated(sheet,assignment);
    }

    public void modifyCook(User cook, Assignment asg) throws UseCaseLogicException {
        modifySheet(currentSheet);
        currentSheet.setNewCook(cook, asg);
        notifyCookModified(asg, cook);
    }

    public void modifyTurn(Turn turn, Assignment asg) throws UseCaseLogicException {
        modifySheet(currentSheet);
        currentSheet.setNewTurn(turn, asg);
        notifyNewTurnInAssignment(asg, turn);
    }

    public void modifyTask(Recipe task, Assignment asg) throws UseCaseLogicException {
        modifySheet(currentSheet);
        currentSheet.setNewItem(task, asg);
        notifyItemModified(task, asg);
    }

    public void assignPortion(int portion, Assignment asg) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.assignPortion(portion, asg);
            notifyPortionAdded(asg, portion);
        } else {
            throw new UseCaseLogicException();
        }
    }

    public void modifyPortion(int newPortion, Assignment asg) throws UseCaseLogicException {
        assignPortion(newPortion, asg);
    }

    public void assignTime(int time, Assignment asg) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.assignTime(time, asg);
            notifyTimeAdded(asg, time);
        } else {
            throw new UseCaseLogicException();
        }
    }

    public void writeNote(String note) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.setNote(note);
            notifyNoteAdded(note);
        } else {
            throw new UseCaseLogicException();
        }
    }

    public SummarySheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(SummarySheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public List<SheetEventReceiver> getEventReceivers() {
        return eventReceivers;
    }

    public void setEventReceivers(List<SheetEventReceiver> eventReceivers) {
        this.eventReceivers = eventReceivers;
    }
}