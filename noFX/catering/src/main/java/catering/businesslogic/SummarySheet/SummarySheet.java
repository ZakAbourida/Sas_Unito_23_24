package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.ItemBook;
import catering.businesslogic.user.Cook;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import catering.persistence.UpdateHandler;

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

    public SummarySheet() {
        this.assignments = new ArrayList<>();
        this.extraTask = new ArrayList<>();
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SummarySheet(User owner, ServiceInfo service) {
        this();
        this.owner = owner;
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

        if (result.length > 0 && result[0] > 0) {
            System.out.println("SummarySheet salvato con ID: " + sheet.id);
        } else {
            System.out.println("Errore durante il salvataggio del SummarySheet.");
        }
    }

    public static void deleteSheet(SummarySheet sheet) {
        String query = "DELETE FROM summarysheet WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, sheet.getId());
            }
        });
    }

    public static SummarySheet loadSummarySheetById(int id) {
        final SummarySheet[] sheet = {null};
        String query = "SELECT * FROM summarysheet WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    sheet[0] = new SummarySheet(User.loadUserById(rs.getInt("owner")), ServiceInfo.loadServiceInfoById(rs.getInt("service_id")));
                    sheet[0].setNote(rs.getString("note"));
                    sheet[0].id = rs.getInt("id");
                    // Carica altri dettagli del foglio riepilogativo se necessario...
                }
            }
        });
        return sheet[0];
    }

    public static List<SummarySheet> loadAllSummarySheets() {
        List<SummarySheet> sheets = new ArrayList<>();
        String query = "SELECT * FROM summarysheet";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    SummarySheet sheet = new SummarySheet();
                    sheet.id = rs.getInt("id");
                    sheet.note = rs.getString("note");
                    sheet.owner = User.loadUserById(rs.getInt("owner"));
                    sheet.service = ServiceInfo.loadServiceInfoById(rs.getInt("service_id"));
                    sheet.menu = Menu.loadMenuById(rs.getInt("menu"));
                    // assignments e extraTask non caricate qui
                    sheets.add(sheet);
                }
            }
        });
        return sheets;
    }

    public String testString() {
        StringBuilder result = new StringBuilder();
        result.append("SummarySheet ID: ").append(id).append("\n");
        result.append("Owner: ").append(owner != null ? owner.getUserName() : "N/A").append("\n");
        result.append("Note: ").append(note != null ? note : "N/A").append("\n");

        result.append("Menu:\n");
        if (menu != null) {
            result.append(menu.testString()).append("\n");
        } else {
            result.append("N/A\n");
        }

        result.append("Assignments:\n");
        if (assignments != null) {
            for (Assignment a : assignments) {
                result.append(a != null ? a.toString() : "N/A").append("\n");
            }
        }

        result.append("Extra Tasks:\n");
        if (extraTask != null) {
            for (ItemBook ib : extraTask) {
                result.append(ib != null ? ib.toString() : "N/A").append("\n");
            }
        }

        return result.toString();
    }

    public int getId() {
        return id;
    }
}