package catering.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UpdateHandler {
    void handleUpdate(PreparedStatement ps) throws SQLException;
}