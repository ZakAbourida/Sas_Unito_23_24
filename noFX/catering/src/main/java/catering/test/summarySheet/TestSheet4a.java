package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.SummarySheet.Assignment;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestSheet4a {

    public static void main(String[] args) {
        try {
            System.out.println("LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
            System.out.println(currentUser);

            System.out.println("\nGET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            EventInfo selectedEvent = events.get(0); // Selezionare il primo evento per il test
            System.out.println(selectedEvent);

            ServiceInfo selectedService = selectedEvent.getServices().get(0); // Selezionare il primo servizio per il test
            System.out.println(selectedService);

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

            // Selezione dell'assignment da modificare
            System.out.println("\nAssignments:");
            List<Assignment> assignments = selectedSheet.getAssignments();
            for (int i = 0; i < assignments.size(); i++) {
                System.out.println((i + 1) + ": " + assignments.get(i).toString());
            }

            System.out.print("Seleziona il numero dell'assignment da modificare: ");
            int assignmentIndex = scanner.nextInt() - 1;
            Assignment selectedAssignment = assignments.get(assignmentIndex);

            // Caricare tutti gli utenti disponibili
            System.out.println("\nALL USERS");
            List<User> allUsers = CatERing.getInstance().loadAllUsers();
            for (int i = 0; i < allUsers.size(); i++) {
                System.out.println((i + 1) + ": " + allUsers.get(i).getUserName());
            }

            System.out.print("Seleziona il numero del nuovo cuoco: ");
            int userIndex = scanner.nextInt() - 1;
            User newCook = allUsers.get(userIndex);

            CatERing.getInstance().getSummarySheetManager().modifyCook(newCook, selectedAssignment);

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
