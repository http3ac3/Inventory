package vlsu.inventory.utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RequestUtility {
    public static ResultSet SelectQuery(Connection connection, String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception ex) {
            return null;
        }
    }
}
