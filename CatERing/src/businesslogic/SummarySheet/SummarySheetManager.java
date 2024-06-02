package businesslogic.SummarySheet;


import businesslogic.event.Event;
import businesslogic.event.Service;
import businesslogic.menu.Menu;
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

    // Altri metodi di notifica per eventReceivers

    // Operazioni per gestire SummarySheet
    public SummarySheet createSummarySheet(User owner, Service service, Event event, Menu menu) {
        SummarySheet sheet = SummarySheet.create(owner, service, event, menu);
        notifySheetCreated(sheet);
        return sheet;
    }

    public boolean deleteSheet(SummarySheet sheet) {
        // Implementazione per eliminare un SummarySheet
        notifySheetDeleted(sheet);
        return true;
    }

    // Altri metodi per gestire SummarySheet
}