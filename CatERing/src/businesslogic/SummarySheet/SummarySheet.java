package businesslogic.SummarySheet;

import businesslogic.event.Event;
import businesslogic.event.Service;
import businesslogic.menu.Menu;
import businesslogic.user.User;

import java.util.List;

public class SummarySheet {
    private String note;
    private List<Assignment> assignments;

    public SummarySheet(String note) {
        this.note = note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Operazioni specifiche per SummarySheet

    public static SummarySheet create(User owner, Service service, Event event, Menu menu) {
        // Implementazione della creazione di un SummarySheet
        return new SummarySheet("Note iniziali");
    }

    public boolean isInProgress() {
        // Implementazione della logica per verificare se è in corso
        return false;
    }

    public boolean isOwner(User user) {
        // Implementazione della logica per verificare se l'utente è il proprietario
        return false;
    }

    // Altri metodi per SummarySheet
}