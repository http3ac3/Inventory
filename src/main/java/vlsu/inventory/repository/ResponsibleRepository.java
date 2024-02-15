package vlsu.inventory.repository;

import org.springframework.stereotype.Repository;
import vlsu.inventory.model.Responsible;
import vlsu.inventory.service.ConnectionService;
import vlsu.inventory.utility.RequestUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResponsibleRepository {
    private final ConnectionService connectionService;

    public ResponsibleRepository(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
    public Responsible getById(int id) {
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM responsible WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            Responsible responsible = new Responsible();
            while (set.next()) {
                responsible.setId(set.getInt("id"));
                responsible.setFirstName(set.getString("first_name"));
                responsible.setLastName(set.getString("last_name"));
                responsible.setPatronymic(set.getString("patronymic"));
                responsible.setPhoneNumber(set.getString("phone_number"));
                responsible.setPosition(set.getString("position"));
                responsible.setFullName();
            }
            return responsible;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<Responsible> getAll() {
        try (Connection connection = connectionService.getConnection()) {
            ResultSet set = RequestUtility.SelectQuery(connection, "SELECT * FROM responsible");
            List<Responsible> responsibles = new ArrayList<>();
            while (set.next()) {
                Responsible responsible = new Responsible();
                responsible.setId(set.getInt("id"));
                responsible.setFirstName(set.getString("first_name"));
                responsible.setLastName(set.getString("last_name"));
                responsible.setPatronymic(set.getString("patronymic"));
                responsible.setPhoneNumber(set.getString("phone_number"));
                responsible.setPosition(set.getString("position"));
                responsible.setFullName();
                responsibles.add(responsible);
            }
            return responsibles;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Responsible getByLogin(String username, String password) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users u " +
                            "JOIN responsible r ON r.id = u.responsible_id " +
                            "WHERE u.username = ? AND u.password = ?"
            );
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            Responsible responsible = new Responsible();
            while (set.next()) {
                responsible.setId(set.getInt("responsible_id"));
                responsible.setFirstName(set.getString("first_name"));
                responsible.setLastName(set.getString("last_name"));
                responsible.setPatronymic(set.getString("patronymic"));
                responsible.setPhoneNumber(set.getString("phone_number"));
                responsible.setPosition(set.getString("position"));
                responsible.setFullName();
            }
            return responsible;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
