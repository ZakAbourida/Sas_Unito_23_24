package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.List;

public class TestMenuService {
    public static void main(String[] args) {
        try {
            System.out.println("LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
            System.out.println("Utente loggato: " + currentUser.getUserName());
            System.out.println("------------------------------------");

            // Get event info and select an event
            System.out.println("GET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo selectedEvent = events.get(0); // Selezionare il primo evento per il test
            System.out.println(selectedEvent);
            System.out.println("------------------------------------");

            // Verifica e seleziona un servizio
            System.out.println("SERVIZI DISPONIBILI PER L'EVENTO SELEZIONATO:");
            for (ServiceInfo service : selectedEvent.getServices()) {
                System.out.println(service);
            }
            System.out.println("------------------------------------");

            // Selezionare un servizio specifico per il test
            ServiceInfo selectedService = selectedEvent.getServices().get(0); // Selezionare il primo servizio per il test
            System.out.println("SERVIZIO SELEZIONATO:");
            System.out.println(selectedService);
            System.out.println("------------------------------------");

            // Caricare il foglio riepilogativo per il servizio selezionato
            System.out.println("TEST LOAD SUMMARY SHEET FOR SELECTED SERVICE");
            List<SummarySheet> summarySheets = CatERing.getInstance().loadAllSummarySheetsForService(selectedService.getId());
            if (summarySheets.isEmpty()) {
                System.out.println("Nessun foglio riepilogativo trovato per il servizio selezionato.");
            } else {
                for (SummarySheet sheet : summarySheets) {
                    System.out.println(sheet.testString());
                }
            }
            System.out.println("------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
