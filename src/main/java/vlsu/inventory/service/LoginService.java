package vlsu.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import vlsu.inventory.utility.RequestUtility;

import java.sql.Connection;
import java.sql.ResultSet;

@Service
@RequestScope
public class LoginService {
    private final ConnectionService connectionService;
    public LoginService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
    public String auth(String username, String password) {
        connectionService.setUsername(username);
        connectionService.setPassword(password);
        try (Connection con = connectionService.getConnection()) {
            ResultSet set = RequestUtility.SelectQuery(con, "SELECT CURRENT_ROLE();");
            String role = "";
            while (set.next()) {
                role = set.getString("current_role()");
            }
            return role;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
