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

    private ServiceInfo service;
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
        this.service = service;

    }

    public boolean isInProgress() {
        return false;
    }

    public boolean isOwner(User user) {
        return true;
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
        String query = "INSERT INTO summarysheet (note, owner, service_id, menu) VALUES (?, ?, ?, ?)";
        int[] result = PersistenceManager.executeBatchUpdate(query, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                String escapedNote = sheet.note != null ? PersistenceManager.escapeString(sheet.note) : "";
                ps.setString(1, escapedNote);
                ps.setInt(2, sheet.owner.getId());
                ps.setInt(3, sheet.service.getId());
                ps.setInt(4, sheet.menu != null ? sheet.menu.getId() : 0);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (rs.next()) {
                    sheet.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {
            // Salva assegnazioni e compiti extra, se presenti
            // ...
        }
    }

    public String testString() {
        StringBuilder result = new StringBuilder();
        result.append("Owner: ").append(owner.getUserName()).append("\n");

        result.append("Menu:\n");
        Menu associatedMenu = service.getMenu(service.getId());
        if (associatedMenu != null) {
            result.append(associatedMenu.testString()).append("\n");
        }

        result.append("Assignments:\n");
        for (Assignment a : assignments) {
            result.append(a.toString()).append("\n");
        }

        result.append("Extra Tasks:\n");
        for (ItemBook ib : extraTask) {
            result.append(ib.toString()).append("\n");
        }

        result.append("Note: ").append(note).append("\n");

        return result.toString();
    }

    public int getId() {
        return id;
    }
}







