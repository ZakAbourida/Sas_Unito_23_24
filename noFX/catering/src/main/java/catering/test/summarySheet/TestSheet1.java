package catering.test.summarySheet;


import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.businesslogic.SummarySheet.SummarySheet;


import java.util.ArrayList;
public class TestSheet1 {
    /**
     * OP.: CREATE
     * */
    public static void main(String[] args) {
        try {
            System.out.println("LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
            System.out.println(currentUser);

            System.out.println("\nGET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo selectedEvent = events.get(0);
            System.out.println(selectedEvent);

            ServiceInfo selectedService = selectedEvent.getServices().get(0);
            System.out.println("Servizio selezionato:");
            System.out.println(selectedService);

            Menu associatedMenu = selectedService.getMenu(selectedService.getId());
            if (associatedMenu == null) {
                System.out.println("Il servizio selezionato non ha un menu. Creazione del menu di esempio...");
                // Crea un menu di esempio per il test
                associatedMenu = CatERing.getInstance().getMenuManager().createMenu("Menu di esempio");
                selectedService.setMenu(associatedMenu);
            }

            System.out.println("\nCREATE SUMMARY SHEET");
            SummarySheet summarySheet = CatERing.getInstance().getSummarySheetManager().createSummarySheet(selectedService);
            if (summarySheet != null) {

                // Stampa il foglio riepilogativo con tutti i suoi oggetti associati
                System.out.println("\nDettagli del Summary Sheet:");
                System.out.println(summarySheet.testString());
            } else {
                System.out.println("Errore: il foglio riepilogativo non è stato creato.");
            }

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


