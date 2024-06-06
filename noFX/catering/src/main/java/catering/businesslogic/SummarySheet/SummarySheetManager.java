package catering.businesslogic.SummarySheet;



import catering.businesslogic.CatERing;
import catering.businesslogic.Turn.Turn;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.Cook;
import catering.businesslogic.user.User;

import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

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

    private void notifyAssignmentCreated(Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateAssignmentCreated(currentSheet, asg);
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

    private void notifyCookModified(Cook cook, Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateCookInAssignment(asg);
        }
    }

    private void notifyNewTurnInAssignment(Turn turn, Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateTurnInAssignment(asg);
        }
    }

    private void notifyItemModified(ItemBook task, Assignment asg) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateItemModified(task, asg);
        }
    }

    private void notifyItemsRearanged(Recipe item) {
        //TODO: da finire
    }




    public SummarySheet createSummarySheet(ServiceInfo service) throws SQLException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (user.isChef() && service.isAssignedChef(user) && service.isAssignedMenu()) {
            SummarySheet sheet = new SummarySheet(user,service);
            service.setSheet(sheet);
            setCurrentSheet(sheet);
            notifySheetCreated(sheet);
            return sheet;
        }
        //TODO: gestire i casi di errore
            /*else if(!user.isChef()){
                throws Object UseCaseLogicException;
            }*/
        return null;
    }

    public void deleteSheet(SummarySheet sheet) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (sheet.isOwner(user) && !sheet.isInProgress()) {
            notifySheetDeleted(sheet);
        }
    }

    public boolean modifySheet(SummarySheet sheet) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (sheet.isOwner(user) && !sheet.isInProgress()) {
            return sheet.isOwner(user);
        } else return false;
    }

    public void addPreparationOrRecipe(Recipe item) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            List<Recipe> items = new ArrayList<>();
            items.add(item);
            notifyItemAdded(items);
        }
    }

    public void moveRecipePreparation(Recipe item, int pos) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.moveRecipePreparation(item, pos);
            notifyItemsRearanged(item);
        }
    }

    public void createAssignment(Cook cook, Turn turn, ItemBook item){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            Assignment asg = currentSheet.createAssignment(cook, turn, item);
            notifyAssignmentCreated(asg);
        }
    }

    public void modifyCook(Cook cook, Assignment asg){
        modifySheet(currentSheet);
        currentSheet.setNewCook(cook,asg);
        notifyCookModified(cook,asg);
    }

    public void modifyTurn(Turn turn, Assignment asg){
        modifySheet(currentSheet);
        currentSheet.setNewTurn(turn,asg);
        notifyNewTurnInAssignment(turn,asg);
    }

    public void modifyTask(ItemBook task, Assignment asg){
        modifySheet(currentSheet);
        currentSheet.setNewItem(task,asg);
        notifyItemModified(task,asg);
    }

    public void assignPortion(int portion, Assignment asg){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.assignPortion(portion, asg);
            notifyPortionAdded(asg, portion);
        }
    }

    public void modifyPortion(int newPortion, Assignment asg){
        assignPortion(newPortion, asg);
    }

    public void assignTime(int time, Assignment asg){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.assignTime(time, asg);
            notifyTimeAdded(asg, time);
        }
    }

    public void writeNote(String note){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.setNote(note);
            notifyNoteAdded(note);
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