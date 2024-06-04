package catering.businesslogic.event;

import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;


public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;

    private Menu menu;
    private SummarySheet sheet;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ArrayList<ServiceInfo> result = new ArrayList<>();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, proposed_menu_id " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");

                // Carica il menu associato se presente
                int menuId = rs.getInt("proposed_menu_id");
                if (menuId > 0) {
                    serv.menu = Menu.loadMenuById(menuId);
                }

                result.add(serv);
            }
        });

        return result;
    }

    public Menu getMenu(int serviceId) {
        if (this.menu == null) {
            // Carica il menu dal database se non è già caricato
            String query = "SELECT proposed_menu_id FROM services WHERE id = " + serviceId;
            PersistenceManager.executeQuery(query, rs -> {
                    int menuId = rs.getInt("proposed_menu_id");
                    if (menuId > 0) {
                        menu = Menu.loadMenuById(menuId);
                    }
            });
        }
        return this.menu;
    }

    public boolean isAssignedChef(User user) {
        return false;
    }

    public boolean isAssignedMenu() {
        return false;
    }

    public SummarySheet getSheet() {
        return sheet;
    }

    public void setSheet(SummarySheet sheet) {
        this.sheet = sheet;
    }

    public void setMenu(Menu menuDiEsempio) {
        this.menu = menuDiEsempio;
    }
}