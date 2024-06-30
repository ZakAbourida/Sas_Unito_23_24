package catering.test.summarySheet;

import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
            executeSqlFile(stmt, "/database/catering_init.sql");

            System.out.println("Database inizializzato con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dropTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {

            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");


            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            List<String> tables = new ArrayList<>();


            while (rs.next()) {
                tables.add(rs.getString(1));
            }


            for (String table : tables) {
                stmt.executeUpdate("DROP TABLE IF EXISTS " + table);
                System.out.println("Tabella " + table + " eliminata.");
            }

            // Riabilita i controlli delle chiavi esterne
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    private static void executeSqlFile(Statement stmt, String resourcePath) throws IOException, SQLException {
        InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("File not found: " + resourcePath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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