package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.SummarySheet.SummarySheet;

import java.util.List;
import java.util.Scanner;

public class TestSheet1a {
    /**
     * OP.: DELETE
     * */
    public static void main(String[] args) {
        System.out.println("TEST LOAD ALL SUMMARY SHEETS");
        List<SummarySheet> sheets = CatERing.getInstance().loadAllSummarySheets();

        int count = 1;
        for (SummarySheet sheet : sheets) {
            System.out.println("---------- Sheet " + count + " ----------");
            System.out.println(sheet.testString());
            count++;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleziona il numero del foglio da eliminare (0 per annullare): ");
        int selection = scanner.nextInt();

        if (selection > 0 && selection <= sheets.size()) {
            SummarySheet sheetToDelete = sheets.get(selection - 1);
            System.out.println("Eliminazione del foglio selezionato...");
            CatERing.getInstance().getSummarySheetManager().deleteSheet(sheetToDelete);
            System.out.println("Foglio eliminato con successo.");
        } else if (selection == 0) {
            System.out.println("Operazione annullata.");
        } else {
            System.out.println("Selezione non valida.");
        }

        scanner.close();
    }
}
