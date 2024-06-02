package businesslogic.SummarySheet;

import businesslogic.CatERing;
import businesslogic.event.Service;
import businesslogic.recipe.ItemBook;
import businesslogic.user.User;



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

    public void notifySheetCreated(SummarySheet sheet) {
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
            receiver.updateItemAdded(currentSheet,item);
        }
    }
    private void notifyItemsRearanged(ItemBook item) {
        for (SheetEventReceiver receiver : eventReceivers) {
            receiver.updateExtraTaskRearranged(currentSheet,item);
        }
    }


    public void createSummarySheet(Service service) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(user.isChef() && service.isAssignedChef(user) && service.isAssignedMenu()){
            SummarySheet sheet = new SummarySheet(user);
            setCurrentSheet(sheet);
            notifySheetCreated(sheet);
        }
    }

    public void deleteSheet(SummarySheet sheet) {
        // Implementazione per eliminare un SummarySheet
        notifySheetDeleted(sheet);
    }

    public boolean modifySheet(SummarySheet sheet) {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        return currentSheet.isOwner(user);
    }

    public void addPreparationOrRecipe(ItemBook item){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(currentSheet.isOwner(user))currentSheet.addExtraTask(item);
        notifyItemAdded(item);
    }

    public void moveRecipePreparation(ItemBook item, ItemBook item2){
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(currentSheet.isOwner(user)){
            currentSheet.moveRecipePreparation(item,item2);
            notifyItemsRearanged(item);
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