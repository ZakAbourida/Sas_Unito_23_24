package businesslogic.event;

import businesslogic.SummarySheet.SummarySheet;
import businesslogic.user.User;

public class Service {
    public SummarySheet sheet;
    public boolean isAssignedChef(User chef) {
        // Implementazione della logica per verificare se il cuoco è assegnato
        return false;
    }

    public boolean isAssignedMenu() {
        // Implementazione della logica per verificare se il menu è assegnato
        return false;
    }

    public boolean isInProgress() {
        // Implementazione della logica per verificare se il servizio è in corso
        return false;
    }

    public void setSheet(SummarySheet sheet){
        this.sheet = sheet;
    }
}
