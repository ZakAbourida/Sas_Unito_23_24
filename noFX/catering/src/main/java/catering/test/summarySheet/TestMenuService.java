package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;

public class TestMenuService {
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

            // Verifica tutti i servizi presenti per l'evento selezionato
            System.out.println("\nSERVIZI DISPONIBILI PER L'EVENTO SELEZIONATO:");
            for (ServiceInfo service : selectedEvent.getServices()) {
                System.out.println(service);
            }

            // Selezionare un servizio che dovrebbe avere un menu associato
            ServiceInfo selectedService = selectedEvent.getServices().get(1); // Assumiamo che questo servizio abbia un menu associato
            System.out.println("\nSERVIZIO SELEZIONATO:");
            System.out.println(selectedService);

            Menu associatedMenu = selectedService.getMenu(selectedService.getId());
            if (associatedMenu != null) {
                System.out.println("Menu associato al servizio: " + associatedMenu.getId() + " - " + associatedMenu.getTitle());
                System.out.println("Dettagli del menu: ");
                System.out.println(associatedMenu.testString());
            } else {
                System.out.println("Il servizio selezionato non ha un menu associato.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
