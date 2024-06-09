package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestSheet3 {
    public static void main(String[] args) {
        try {
            // Effettua il login
            System.out.println("LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
            System.out.println("Utente loggato: " + currentUser.getUserName());

            // Ottiene le informazioni sugli eventi e seleziona un evento
            System.out.println("\nGET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo selectedEvent = events.get(0); // Seleziona il primo evento per il test
            System.out.println(selectedEvent);

            // Seleziona un servizio specifico per l'evento
            ServiceInfo selectedService = selectedEvent.getServices().get(0); // Seleziona il primo servizio per il test
            System.out.println(selectedService);

            // Carica i fogli riepilogativi per il servizio selezionato
            System.out.println("\nSummarySheets trovati per il servizio selezionato:");
            List<SummarySheet> sheets = CatERing.getInstance().loadAllSummarySheetsForService(selectedService.getId());
            for (int i = 0; i < sheets.size(); i++) {
                System.out.println((i + 1) + ": SummarySheet ID: " + sheets.get(i).getId());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Seleziona il numero del SummarySheet desiderato: ");
            int sheetIndex = scanner.nextInt() - 1;
            SummarySheet selectedSheet = sheets.get(sheetIndex);
            CatERing.getInstance().getSummarySheetManager().setCurrentSheet(selectedSheet); // Imposta il foglio corrente

            // Stampa il foglio riepilogativo con tutti i suoi oggetti associati
            System.out.println("\nDettagli del Summary Sheet:");
            System.out.println(selectedSheet.testString());

            // Selezione dell'extra task da riposizionare
            System.out.println("\nExtra Tasks:");
            List<Recipe> extraTasks = selectedSheet.getExtraTask();
            for (int i = 0; i < extraTasks.size(); i++) {
                System.out.println((i + 1) + ": " + extraTasks.get(i).getName());
            }

            System.out.print("Seleziona il numero dell'extra task da riposizionare: ");
            int taskIndex = scanner.nextInt() - 1;
            Recipe selectedTask = extraTasks.get(taskIndex);

            System.out.print("Inserisci la nuova posizione (1-" + extraTasks.size() + "): ");
            int newPosition = scanner.nextInt() - 1;

            // Riposiziona l'extra task selezionato
            CatERing.getInstance().getSummarySheetManager().moveRecipePreparation(selectedTask, newPosition);

            // Stampa il foglio riepilogativo aggiornato
            System.out.println("\nDettagli del Summary Sheet aggiornato:");
            System.out.println(selectedSheet.testString());

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
