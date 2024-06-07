package catering.businesslogic.SummarySheet;

import catering.businesslogic.Turn.Turn;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.Cook;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;
import catering.persistence.UpdateHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SummarySheet {
    private int id;
    private String note;
    private User owner;
    private Menu menu;
    private ServiceInfo service;
    private List<Assignment> assignments;
    private List<Recipe> extraTask;

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
        this.service = service;
        this.menu = service.getMenu(service.getId()); // Carica il menu associato al servizio
    }

    public boolean isInProgress() {
        return false;
    }

    public boolean isOwner(User user) {
        return true;
    }

    public void addExtraTask(Recipe item) {
        if (!this.extraTask.contains(item)) {
            this.extraTask.add(item);
        }
    }

    public void moveRecipePreparation(Recipe item, int pos) {
        extraTask.remove(item);
        extraTask.add(pos, item);
    }

    public Assignment createAssignment(User cook, Turn turn, Recipe item) {
        Assignment asg = new Assignment(cook, turn, item);
        assignments.add(asg);
        return asg;
    }

    public void assignPortion(int portion, Assignment asg) {
        asg.setPortion(portion);
    }

    public void assignTime(int time, Assignment asg) {
        asg.setTime(time);
    }

    public void setNewCook(User cook, Assignment asg) {
        asg.setCook(cook);
    }

    public void setNewTurn(Turn turn, Assignment asg) {
        asg.setTurn(turn);
    }

    public void setNewItem(Recipe task, Assignment asg) {
        asg.setTask(task);
    }

    // METODI PER LA PERSISTENZA

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
        } else {
            System.out.println("Errore durante il salvataggio del SummarySheet.");
        }
    }

    public static void deleteSheet(SummarySheet sheet) {
        deleteExtraTasks(sheet);
        String query = "DELETE FROM summarysheet WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, sheet.getId());
            }
        });
    }

    private static void deleteExtraTasks(SummarySheet sheet) {
        String query = "DELETE FROM extratask WHERE sheet = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, sheet.getId());
            }
        });
    }

    public static void addItem(SummarySheet sheet, List<Recipe> items) {
        String query = "INSERT INTO extratask (task, sheet) VALUES (?, ?)";
        int[] result = PersistenceManager.executeBatchUpdate(query, items.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                Recipe item = items.get(batchCount);
                ps.setInt(1, item.getId());
                ps.setInt(2, sheet.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // Non ci sono ID generati da gestire per questi inserimenti
            }
        });

        if (result.length == items.size()) {
            for (Recipe item : items) {
                sheet.addExtraTask(item); // Aggiunge l'elemento alla lista extraTask del foglio riepilogativo
            }
            System.out.println("Tutti gli extra task sono stati aggiunti al SummarySheet con ID: " + sheet.getId());
        } else {
            System.out.println("Errore durante l'aggiunta degli extra task al SummarySheet.");
        }
    }
    public static void saveNewAssignment(SummarySheet sheet, Assignment assignment) {
        if (sheet == null || sheet.getId() == 0) {
            throw new IllegalArgumentException("SummarySheet cannot be null and must have a valid ID");
        }

        String query = "INSERT INTO assignment (turn, cook, recipe, sheet, portion, time) VALUES (?, ?, ?, ?, ?, ?)";
        int[] result = PersistenceManager.executeBatchUpdate(query, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, assignment.getTurn().getId());
                ps.setInt(2, assignment.getCook().getId());
                ps.setInt(3, assignment.getRecipe().getId());
                ps.setInt(4, sheet.getId());
                ps.setInt(5, assignment.getPortion());
                ps.setInt(6, assignment.getTime());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (rs.next()) {
                    assignment.setId(rs.getInt(1));
                }
            }
        });

        if (result.length > 0 && result[0] > 0) {
            System.out.println("Assignment salvato!");
        } else {
            System.out.println("Errore durante il salvataggio dell'Assignment.");
        }
    }

    public static void addPortion(Assignment asg, int portion) {
        String query = "UPDATE assignment SET portion = ? WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, portion);
                ps.setInt(2, asg.getId());
            }
        });
        asg.setPortion(portion);
    }

    public static void addTime(Assignment asg, int time) {
        String query = "UPDATE assignment SET time = ? WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, time);
                ps.setInt(2, asg.getId());
            }
        });
        asg.setTime(time);
    }

    public static void addNote(SummarySheet sheet, String note) {
        String query = "UPDATE summarysheet SET note = ? WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setString(1, PersistenceManager.escapeString(note));
                ps.setInt(2, sheet.getId());
            }
        });
        sheet.setNote(note);
    }

    public static void modifyCookInAssignment(Assignment asg, User cook) {
        String query = "UPDATE assignment SET cook = ? WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, cook.getId());
                ps.setInt(2, asg.getId());
            }
        });
        asg.setCook(cook);
    }

    public static void modifyTurnInAssignment(Assignment asg, Turn turn) {
        String query = "UPDATE assignment SET turn = ? WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, turn.getId());
                ps.setInt(2, asg.getId());
            }
        });
        asg.setTurn(turn);
    }

    public static void modifyTaskInAssignment(Assignment asg, Recipe task) {
        String query = "UPDATE assignment SET recipe = ? WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, task.getId());
                ps.setInt(2, asg.getId());
            }
        });
        asg.setTask(task);
    }

    public static void deleteAssignment(Assignment asg) {
        String query = "DELETE FROM assignment WHERE id = ?";
        PersistenceManager.executeUpdate(query, new UpdateHandler() {
            @Override
            public void handleUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, asg.getId());
            }
        });
    }

    public static List<SummarySheet> loadAllSummarySheets() {
        List<SummarySheet> sheets = new ArrayList<>();
        String query = "SELECT * FROM summarysheet";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                    SummarySheet sheet = new SummarySheet();
                    sheet.id = rs.getInt("id");
                    sheet.note = rs.getString("note");
                    sheet.owner = User.loadUserById(rs.getInt("owner"));
                    sheet.service = ServiceInfo.loadServiceInfoById(rs.getInt("service_id"));
                    sheet.menu = Menu.loadMenuById(rs.getInt("menu"));
                    sheets.add(sheet);
            }
        });
        return sheets;
    }

    public static List<SummarySheet> loadAllSummarySheetsForService(int serviceId) {
        List<SummarySheet> sheets = new ArrayList<>();
        String query = "SELECT * FROM summarysheet WHERE service_id = " + serviceId;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                    SummarySheet sheet = new SummarySheet();
                    sheet.id = rs.getInt("id");
                    sheet.note = rs.getString("note");
                    sheet.owner = User.loadUserById(rs.getInt("owner"));
                    sheet.service = ServiceInfo.loadServiceInfoById(rs.getInt("service_id"));
                    sheet.menu = Menu.loadMenuById(rs.getInt("menu"));
                    sheets.add(sheet);
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
        } else {
            result.append("N/A\n");
        }

        result.append("Extra Tasks:\n");
        if (extraTask != null) {
            for (Recipe ib : extraTask) {
                result.append(ib != null ? ib.toString() : "N/A").append("\n");
            }
        } else {
            result.append("N/A\n");
        }

        return result.toString();
    }

    public int getId() {
        return this.id;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Recipe> getExtraTask() {
        return extraTask;
    }

    public Menu getMenu() {
        return this.menu;
    }
}