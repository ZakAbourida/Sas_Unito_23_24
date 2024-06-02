package businesslogic.SummarySheet;

import businesslogic.event.Event;
import businesslogic.event.Service;
import businesslogic.menu.Menu;
import businesslogic.user.User;
import businesslogic.user.UserManager;

import java.util.*;

import static businesslogic.user.UserManager.getCurrentUser;

public class SummarySheetManager {
    private SummarySheet currentSheet;
    private UserManager userManager;

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

    // Altri metodi di notifica per eventReceivers

    // Operazioni per gestire SummarySheet
    public SummarySheet createSummarySheet(Service service) {
        User user = getCurrentUser();
        if(user.isChef()){
            SummarySheet sheet = SummarySheet.create(service);
            notifySheetCreated(sheet);
            return sheet;
        }


        return null;
    }

    public boolean deleteSheet(SummarySheet sheet) {
        // Implementazione per eliminare un SummarySheet
        notifySheetDeleted(sheet);
        return true;
    }

    // Altri metodi per gestire SummarySheet
}