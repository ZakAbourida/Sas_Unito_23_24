package catering.businesslogic.SummarySheet;



import catering.businesslogic.Turn.Turn;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.user.Cook;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SummarySheet {
    private int id;
    private String note;
    private User owner;
    private Menu menu;
    private List<Assignment> assignments;
    private List<ItemBook> extraTask;


    public void setNote(String note) {
        this.note = note;
    }

    public SummarySheet(User owner, ServiceInfo service) {
        this.owner = owner;
        this.assignments = new ArrayList<>();
        this.extraTask = new ArrayList<>();
        this.menu = service.getMenu(service.getId());

    }

    public boolean isInProgress() {
        // Implementazione della logica per verificare se è in corso
        return false;
    }

    public boolean isOwner(User user) {
        // Implementazione della logica per verificare se l'utente è il proprietario
        return false;
    }

    public void addExtraTask(ItemBook itemBook) {
        this.extraTask.add(itemBook);
    }

    public void moveRecipePreparation(ItemBook item1, ItemBook item2) {
        int index1 = extraTask.indexOf(item1);
        int index2 = extraTask.indexOf(item2);

        if (index1 == -1 || index2 == -1) {
            return; // Uno o entrambi gli elementi non esistono nella lista
        }

        // Scambia gli elementi
        extraTask.set(index1, item2);
        extraTask.set(index2, item1);
    }

    public Assignment createAssignment(Cook cook, Turn turn, ItemBook itemBook) {
        Assignment asg = new Assignment(cook, turn, itemBook);
        assignments.add(asg);
        return asg;
    }

    public void assignPortion(int portion, Assignment asg) {
        asg.setPortion(portion);
    }

    public void assignTime(Time time, Assignment asg) {
        asg.setTime(time);
    }

    public static void saveNewSummarySheet(SummarySheet sheet) {
        String summarySheetInsert = "INSERT INTO catering.summarysheet (note, owner, menu) VALUES (?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(summarySheetInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, PersistenceManager.escapeString(sheet.note));
                ps.setInt(2, sheet.owner.getId());
                ps.setInt(3, sheet.menu.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // should be only one
                if (count == 0) {
                    sheet.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {
            // Summary sheet inserito correttamente
            System.out.println("SummarySheet salvato con ID: " + sheet.id);
        }
    }

    public int getId() {
        return id;
    }
}







