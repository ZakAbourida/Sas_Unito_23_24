package catering.businesslogic.SummarySheet;



import catering.businesslogic.CatERing;
import catering.businesslogic.Turn.Turn;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.ItemBook;
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

    private void notifyItemAdded(ItemBook item) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateItemAdded(currentSheet, item);
        }
    }

    private void notifyItemsRearanged(ItemBook item) {
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

    private void notifyTimeAdded(Assignment asg, Time time) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateAssignmentTime(asg, time);
        }
    }

    private void notifyNoteAdded(String note) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateSummarySheetNotes(currentSheet, note);
        }
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

    public void addPreparationOrRecipe(ItemBook item) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.addExtraTask(item);
            notifyItemAdded(item);
        }
    }

    public void moveRecipePreparation(ItemBook item, ItemBook item2) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.moveRecipePreparation(item, item2);
            notifyItemsRearanged(item);
        }
    }

    public void createAssignment(Cook cook, Turn turn, ItemBook itemBook){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            Assignment asg = currentSheet.createAssignment(cook, turn, itemBook);
            notifyAssignmentCreated(asg);
        }
    }

    public void modifyCook(Cook cook, Assignment asg){
        //TODO: da completare alla fine
    }

    public void modifyTurn(Turn turn, Assignment asg){
        //TODO: da completare alla fine
    }

    public void modifyTask(ItemBook task, Assignment asg){
        //TODO: da completare alla fine
    }

    public void assignPortion(int portion, Assignment asg){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSheet.isOwner(user)) {
            currentSheet.assignPortion(portion, asg);
            notifyPortionAdded(asg, portion);
        }
    }

    public void modifyPortion(){
        //TODO: da completare alla fine
    }

    public void assignTime(Time time, Assignment asg){
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

    public List<SummarySheet> loadAllSummarySheets() {
        return SummarySheet.loadAllSummarySheets();
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