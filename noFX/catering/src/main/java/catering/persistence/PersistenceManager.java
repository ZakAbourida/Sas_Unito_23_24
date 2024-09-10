package catering.persistence;

import java.sql.*;

/**
 * Manages database operations such as executing queries, updates, and batch updates.
 * <p>
 * Provides methods for handling SQL operations, including query execution, batch updates,
 * and escaping strings for SQL safety. It also manages database connections and handles
 * SQL exceptions.
 * </p>
 */
public class PersistenceManager {
    private static String url = "jdbc:mysql://localhost:3306/catering?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "root";

    private static int lastId;

    /**
     * Escapes special characters in a string for safe inclusion in SQL queries.
     * <p>
     * This method replaces characters like backslashes, single quotes, double quotes,
     * newlines, and tabs with their escaped equivalents to prevent SQL injection attacks.
     * </p>
     *
     * @param input The input string to be escaped.
     * @return The escaped string.
     */
    public static String escapeString(String input) {
        input = input.replace("\\", "\\\\");
        input = input.replace("\'", "\\\'");
        input = input.replace("\"", "\\\"");
        input = input.replace("\n", "\\n");
        input = input.replace("\t", "\\t");
        return input;
    }

    /**
     * Tests the SQL connection by querying the 'Users' table and printing the results.
     * <p>
     * This method attempts to connect to the database and retrieve user information for
     * verification purposes.
     * </p>
     */
    public static void testSQLConnection() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                System.out.println(name + " ha id = " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Executes a SQL query and processes the results using a {@link ResultHandler}.
     * <p>
     * The query results are handled by the provided {@link ResultHandler}, which processes
     * each row of the {@link ResultSet}.
     * </p>
     *
     * @param query   The SQL query to execute.
     * @param handler The handler to process the query results.
     */
    public static void executeQuery(String query, ResultHandler handler) {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                handler.handle(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Executes a batch update with parameterized queries and handles generated keys.
     * <p>
     * Executes multiple SQL statements in a batch and processes the generated keys using
     * a {@link BatchUpdateHandler}.
     * </p>
     *
     * @param parametrizedQuery The SQL query with parameters.
     * @param itemNumber        The number of items to be processed.
     * @param handler           The handler to process each batch item and generated keys.
     * @return An array of update counts for each command in the batch.
     */
    public static int[] executeBatchUpdate(String parametrizedQuery, int itemNumber, BatchUpdateHandler handler) {
        int[] result = new int[0];
        try (
                Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = conn.prepareStatement(parametrizedQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            for (int i = 0; i < itemNumber; i++) {
                handler.handleBatchItem(ps, i);
                ps.addBatch();
            }
            result = ps.executeBatch();
            ResultSet keys = ps.getGeneratedKeys();
            int count = 0;
            while (keys.next()) {
                handler.handleGeneratedIds(keys, count);
                count++;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * Executes an update statement and retrieves the generated keys.
     * <p>
     * Executes a single SQL update statement and retrieves the generated keys, if any.
     * </p>
     *
     * @param update The SQL update statement to execute.
     * @return The number of rows affected by the update.
     */
    public static int executeUpdate(String update) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            result = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            } else {
                lastId = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Executes an update statement with a custom {@link UpdateHandler}.
     * <p>
     * Executes a SQL update statement and processes it using the provided {@link UpdateHandler}.
     * </p>
     *
     * @param query   The SQL update statement to execute.
     * @param handler The handler to process the update statement.
     * @return The number of rows affected by the update.
     */
    public static int executeUpdate(String query, UpdateHandler handler) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            handler.handleUpdate(ps);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Gets the last generated ID from the most recent update operation.
     * <p>
     * This method returns the ID generated by the most recent update operation that
     * involved an auto-increment column.
     * </p>
     *
     * @return The last generated ID.
     */
    public static int getLastId() {
        return lastId;
    }
}
