package vlsu.inventory.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
@SessionScope
public class ConnectionService {
    @Value("${db.datasource.url}")
    private String dataSourceUrl;
    private String username;
    private String password;
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(dataSourceUrl, username, password);
    }
}
