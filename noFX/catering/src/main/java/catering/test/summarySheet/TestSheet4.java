package catering.test.summarySheet;

import catering.businesslogic.CatERing;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;

import java.util.List;
public class TestSheet4 {
    public static void main(String[] args) {
        try {

            System.out.println("\nTEST LOAD ALL USERS");
            List<User> allUsers = CatERing.getInstance().loadAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("Nessun utente trovato nel database.");
            } else {
                System.out.println("Utenti trovati:");
                for (User user : allUsers) {
                    System.out.println(user.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
