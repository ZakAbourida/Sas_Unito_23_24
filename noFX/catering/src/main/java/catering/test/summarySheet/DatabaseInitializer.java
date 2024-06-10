package catering.test.summarySheet;

import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/catering";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Elimina tutte le tabelle nel database
            dropTables(conn);

            // Legge e esegue il file SQL per creare le tabelle
            executeSqlFile(stmt, "C:\\Users\\danie\\Desktop\\Sas_Unito_23_24\\noFX\\catering\\database\\catering_init.sql");

            System.out.println("Database inizializzato con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dropTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            while (rs.next()) {
                String table = rs.getString(1);
                stmt.executeUpdate("DROP TABLE IF EXISTS " + table);
                System.out.println("Tabella " + table + " eliminata.");
            }
        }
    }

    private static void executeSqlFile(Statement stmt, String filePath) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("/*") || line.startsWith("*/")) {
                    continue; // Ignora commenti e linee vuote
                }
                sql.append(line);
                if (line.endsWith(";")) {
                    String query = sql.toString();
                    try {
                        stmt.execute(query);
                    } catch (SQLException e) {
                        System.err.println("Errore durante l'esecuzione della query: " + query);
                        throw e;
                    }
                    sql.setLength(0);
                }
            }
        }
    }
}