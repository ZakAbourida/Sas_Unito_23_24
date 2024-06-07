package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestSheet2 {
    /**
     * OP.: ADD_RECIPE_PREPARATION
     * */
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

            System.out.println("\nALL RECIPES");
            List<Recipe> allRecipes = CatERing.getInstance().getRecipeManager().getRecipes();
            for (int i = 0; i < allRecipes.size(); i++) {
                System.out.println((i + 1) + ": " + allRecipes.get(i).getName());
            }

            System.out.print("Inserisci i numeri delle ricette da aggiungere (separati da virgola): ");
            scanner.nextLine(); // Consume newline
            String input = scanner.nextLine();
            String[] recipeNumbers = input.split(",");

            List<Recipe> selectedRecipes = new ArrayList<>();
            for (String number : recipeNumbers) {
                int recipeIndex = Integer.parseInt(number.trim()) - 1;
                selectedRecipes.add(allRecipes.get(recipeIndex));
            }

            for (Recipe recipe : selectedRecipes) {
                CatERing.getInstance().getSummarySheetManager().addPreparationOrRecipe(recipe);
            }

            // Stampa il foglio riepilogativo con tutti i suoi oggetti associati
            System.out.println("\nDettagli del Summary Sheet:");
            System.out.println(selectedSheet.testString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}