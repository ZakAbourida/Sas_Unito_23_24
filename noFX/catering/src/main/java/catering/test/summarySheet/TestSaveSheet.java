package catering.test.summarySheet;


import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
public class TestSaveSheet {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();

            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
            System.out.println(currentUser);

            System.out.println("\nTEST GET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo selectedEvent = events.get(0); // Selezionare il primo evento per il test
            System.out.println(selectedEvent);

            ServiceInfo selectedService = selectedEvent.getServices().get(0); // Selezionare il primo servizio per il test
            System.out.println(selectedService);

            if (selectedService.getMenu(selectedService.getId()) == null) {
                System.out.println("Il servizio selezionato non ha un menu. Creazione del menu di esempio...");
                // Crea un menu di esempio per il test
                selectedService.setMenu(CatERing.getInstance().getMenuManager().createMenu("Menu di esempio"));
            }

            System.out.println("\nTEST CREATE SUMMARY SHEET");
            SummarySheet summarySheet = new SummarySheet(currentUser, selectedService);
            summarySheet.setNote("Nota di esempio per il foglio riepilogativo");

            System.out.println("Salvataggio del SummarySheet nel database...");
            SummarySheet.saveNewSummarySheet(summarySheet);
            System.out.println("SummarySheet salvato con ID: " + summarySheet.getId());

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

