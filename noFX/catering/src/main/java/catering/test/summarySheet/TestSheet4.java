package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.Turn.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestSheet4 {
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
            System.out.println("SUMMARY SHEET FOR SELECTED SERVICE");
            List<SummarySheet> summarySheets = CatERing.getInstance().loadAllSummarySheetsForService(selectedService.getId());
            SummarySheet summarySheet;
            if (summarySheets.isEmpty()) {
                summarySheet = CatERing.getInstance().getSummarySheetManager().createSummarySheet(selectedService);
                summarySheet.setNote("Nota di esempio per il foglio riepilogativo");
            } else {
                summarySheet = summarySheets.get(0);
            }
            System.out.println(summarySheet.testString());
            System.out.println("------------------------------------");

            // Verifica che il SummarySheet abbia un ID valido
            if (summarySheet.getId() == 0) {
                throw new IllegalArgumentException("SummarySheet ID non valido");
            }
            List<User> allUsers = CatERing.getInstance().loadAllUsers();

            // Creare n assegnamenti in base alle necessità
            System.out.println("CREATE ASSIGNMENTS");
            List<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            List<Turn> turns = CatERing.getInstance().loadAllTurnForService(selectedService.getId());

            Scanner scanner = new Scanner(System.in);
            boolean createMoreAssignments = true;

            while (createMoreAssignments && !allUsers.isEmpty() && !turns.isEmpty() && !recipes.isEmpty()) {
                System.out.println("Seleziona un utente:");
                for (int j = 0; j < allUsers.size(); j++) {
                    System.out.println((j + 1) + ". " + allUsers.get(j).getUserName());
                }
                int userIndex = scanner.nextInt() - 1;

                System.out.println("Seleziona una ricetta:");
                for (int j = 0; j < recipes.size(); j++) {
                    System.out.println((j + 1) + ". " + recipes.get(j).getName());
                }
                int recipeIndex = scanner.nextInt() - 1;

                System.out.println("Seleziona un turno:");
                for (int j = 0; j < turns.size(); j++) {
                    System.out.println((j + 1) + ". " + turns.get(j).toString());
                }
                int turnIndex = scanner.nextInt() - 1;

                System.out.println("Vuoi inserire la porzione? (true/false)");
                boolean insertPortion = scanner.nextBoolean();
                Integer portion = null;
                if (insertPortion) {
                    System.out.println("Inserisci la porzione:");
                    portion = scanner.nextInt();
                }

                System.out.println("Vuoi inserire il tempo? (true/false)");
                boolean insertTime = scanner.nextBoolean();
                Integer time = null;
                if (insertTime) {
                    System.out.println("Inserisci il tempo:");
                    time = scanner.nextInt();
                }

                CatERing.getInstance().getSummarySheetManager().createAssignment(
                        summarySheet,
                        allUsers.get(userIndex),
                        turns.get(turnIndex),
                        recipes.get(recipeIndex),
                        portion,
                        time
                );

                System.out.println("Vuoi creare un altro assegnamento? (true/false)");
                createMoreAssignments = scanner.nextBoolean();
            }
            System.out.println("------------------------------------");

            // Stampa il foglio riepilogativo aggiornato
            System.out.println("FOGLIO RIEPILOGATIVO AGGIORNATO:");
            System.out.println(summarySheet.testString());
            System.out.println("------------------------------------");

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}